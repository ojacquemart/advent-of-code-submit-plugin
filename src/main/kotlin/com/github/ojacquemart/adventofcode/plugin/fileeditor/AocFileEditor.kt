package com.github.ojacquemart.adventofcode.plugin.fileeditor

import com.github.ojacquemart.adventofcode.plugin.tree.Day
import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefCookie
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandlerAdapter
import org.cef.network.CefRequest.TransitionType
import org.jetbrains.annotations.Nls
import java.beans.PropertyChangeListener
import javax.swing.JComponent

class AocFileEditor(private val file: Day) : FileEditor, DumbAware {
    private val component: AocFileEditorComponent = AocFileEditorComponent(file)

    init {
        val browser: JBCefBrowser = component.browser
        browser.jbCefClient.addLoadHandler(object : CefLoadHandlerAdapter() {
            override fun onLoadStart(cefBrowser: CefBrowser, frame: CefFrame, transitionType: TransitionType) {
                val properties = PropertiesComponent.getInstance()
                val zoom = properties.getFloat("zoom", 0.0f)
                cefBrowser.zoomLevel = zoom.toDouble()
            }
        }, browser.cefBrowser)

        val credentialAttributes = createCredentialAttributes()
        PasswordSafe.instance.getAsync(credentialAttributes)
            .onSuccess { credentials ->
                setCookie(credentials, browser)

            }
    }

    private fun setCookie(
        credentials: Credentials?,
        browser: JBCefBrowser,
    ) {
        val maybePassword = credentials?.password
        maybePassword?.let { password ->
            browser.jbCefCookieManager.setCookie(
                "https://adventofcode.com", JBCefCookie(
                    "session",
                    password.toString(false),
                    ".adventofcode.com",
                    "/",
                    true,
                    false
                ), false
            )
        }
    }

    override fun getComponent(): JComponent = component

    override fun getPreferredFocusedComponent(): JComponent? = null

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getName(): String = "???"

    override fun setState(state: FileEditorState) {}

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
    }

    override fun getCurrentLocation(): FileEditorLocation? = null

    override fun dispose() {
        val cookies: MutableList<JBCefCookie> = component.browser.jbCefCookieManager.cookies
        cookies.removeIf { cookie: JBCefCookie -> cookie.domain != ".adventofcode.com" }
        cookies.removeIf { cookie: JBCefCookie -> cookie.name != "session" }

        if (cookies.isEmpty()) {
            return
        }

        val session = cookies[0]

        val credentialAttributes = createCredentialAttributes()
        val credentials = Credentials("", session.value)
        PasswordSafe.instance.set(credentialAttributes, credentials)
    }

    override fun <T> getUserData(key: Key<T>): T? = null

    override fun <T> putUserData(key: Key<T>, value: T?) {}

    override fun getFile(): VirtualFile = file

    companion object {
        fun createCredentialAttributes(): CredentialAttributes = CredentialAttributes(
            generateServiceName("aoc-submit", "AdventOfCode")
        )
    }
}
