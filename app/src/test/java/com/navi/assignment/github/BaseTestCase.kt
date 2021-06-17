package com.navi.assignment.github

import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass

open class BaseTestCase(val clearMockks: Boolean = true) {

    @Before
    open fun clearMockk() {
        if (clearMockks) {
            cleanUpMockk()
        }
        MockKAnnotations.init(this)
    }

    companion object {
        @JvmStatic
        @BeforeClass
        @AfterClass
        fun cleanUp() {
            cleanUpMockk()
        }

        private fun cleanUpMockk() {
            clearAllMocks()
            unmockkAll()
        }
    }
}