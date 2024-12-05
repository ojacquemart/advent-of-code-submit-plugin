package com.github.ojacquemart.adventofcode.plugin.fileeditor

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.tree.Day
import com.intellij.credentialStore.Credentials
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
import org.cef.CefApp
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandlerAdapter
import org.cef.network.CefRequest.TransitionType
import org.jetbrains.annotations.Nls
import java.beans.PropertyChangeListener
import javax.swing.JComponent

class AocFileEditor(private val file: Day) : FileEditor, DumbAware {

    companion object {
        const val COOKIE_NAME = "session"
        const val USERNAME = "aoc-username"
    }

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

        setCookie(browser)
    }

    private fun setCookie(
        browser: JBCefBrowser,
    ) {
        val password = PasswordSafe.instance.getPassword(Aoc.CREDENTIAL_ATTRS) ?: return

        CefApp.getInstance().onInitialization {
            browser.jbCefCookieManager.setCookie(
                Aoc.URL,
                JBCefCookie(
                    COOKIE_NAME,
                    password,
                    ".${Aoc.DOMAIN}",
                    "/",
                    true,
                    true
                )
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
        val cookies = component.browser.jbCefCookieManager.cookies
        cookies.removeIf { !it.isAocSession() }

        if (cookies.isEmpty()) {
            return
        }

        val session = cookies[0]

        val credentials = Credentials(USERNAME, session.value)
        PasswordSafe.instance.set(Aoc.CREDENTIAL_ATTRS, credentials)
        Aoc.State.session = credentials.password?.toString(false)
    }

    private fun JBCefCookie.isAocSession(): Boolean = domain == ".${Aoc.DOMAIN}" && name == COOKIE_NAME

    override fun <T> getUserData(key: Key<T>): T? = null

    override fun <T> putUserData(key: Key<T>, value: T?) {}

    override fun getFile(): VirtualFile = file

}
