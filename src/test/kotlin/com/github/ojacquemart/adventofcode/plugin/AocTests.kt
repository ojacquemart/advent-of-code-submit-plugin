package com.github.ojacquemart.adventofcode.plugin

import com.github.ojacquemart.adventofcode.plugin.calendar.CalendarDataProvider
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.psi.xml.XmlFile
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.PsiErrorElementUtil
import java.time.LocalDate
import java.time.Month
import java.time.Year

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class AocTests : BasePlatformTestCase() {

    // TODO: clean up the tests

    fun testXMLFile() {
        val psiFile = myFixture.configureByText(XmlFileType.INSTANCE, "<foo>bar</foo>")
        val xmlFile = assertInstanceOf(psiFile, XmlFile::class.java)

        assertFalse(PsiErrorElementUtil.hasErrors(project, xmlFile.virtualFile))

        assertNotNull(xmlFile.rootTag)

        xmlFile.rootTag?.let {
            assertEquals("foo", it.name)
            assertEquals("bar", it.value.text)
        }
    }

    fun testRename() {
        myFixture.testRename("foo.xml", "foo_after.xml", "a2")
    }

    fun testCalendarServiceGetYears() {
        val years = CalendarDataProvider.getYears()

        val currentYear = if (LocalDate.now().month == Month.DECEMBER) Year.now().value else Year.now().value - 1
        assertEquals(currentYear, years.first().year)
        assertEquals(2015, years.last().year)
    }

    override fun getTestDataPath() = "src/test/testData/rename"
}
