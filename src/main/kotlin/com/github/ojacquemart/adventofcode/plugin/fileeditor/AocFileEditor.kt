package com.github.ojacquemart.adventofcode.plugin.fileeditor

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.http.CredentialsManager
import com.github.ojacquemart.adventofcode.plugin.http.HttpClientProvider.SESSION_COOKIE
import com.github.ojacquemart.adventofcode.plugin.tree.Day
import com.intellij.credentialStore.Credentials
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.jcef.JBCefBrowser
import io.ktor.http.*
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.callback.CefCookieVisitor
import org.cef.handler.CefLoadHandlerAdapter
import org.cef.misc.BoolRef
import org.cef.network.CefCookie
import org.cef.network.CefCookieManager
import org.cef.network.CefRequest.TransitionType
import org.jetbrains.annotations.Nls
import java.beans.PropertyChangeListener
import javax.swing.JComponent

class AocFileEditor(private val file: Day) : FileEditor, CefCookieVisitor, DumbAware {

    companion object {
        const val USERNAME = "aoc-username"
    }

    private val component: AocFileEditorComponent = AocFileEditorComponent(file)

    init {
        val browser: JBCefBrowser = component.browser
        browser.jbCefClient.addLoadHandler(object : CefLoadHandlerAdapter() {
            override fun onLoadEnd(browser: CefBrowser?, frame: CefFrame?, httpStatusCode: Int) {
                super.onLoadEnd(browser, frame, httpStatusCode)

                if (httpStatusCode == HttpStatusCode.OK.value) {
                    visitCookies()
                }
            }

            override fun onLoadStart(cefBrowser: CefBrowser, frame: CefFrame, transitionType: TransitionType) {
                val properties = PropertiesComponent.getInstance()
                val zoom = properties.getFloat("zoom", 0.0f)
                cefBrowser.zoomLevel = zoom.toDouble()
            }
        }, browser.cefBrowser)
    }

    private fun visitCookies() {
        CredentialsManager.instance.clear()

        getCookieManager().visitUrlCookies(Aoc.URL, true, this)
    }

    override fun visit(cookie: CefCookie, count: Int, total: Int, delete: BoolRef) =
        when (cookie.name) {
            SESSION_COOKIE -> {
                val credentials = Credentials(USERNAME, cookie.value)
                CredentialsManager.instance.update(credentials)

                false
            }

            else -> true
        }

    private fun getCookieManager(): CefCookieManager =
        CefCookieManager.getGlobalManager()

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
    }

    override fun <T> getUserData(key: Key<T>): T? = null

    override fun <T> putUserData(key: Key<T>, value: T?) {}

    override fun getFile(): VirtualFile = file

}
