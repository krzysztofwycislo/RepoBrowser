package pl.handsome.club.repobrowser.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import pl.handsome.club.repobrowser.util.AppCoroutineDispatchers


@ExperimentalCoroutinesApi
class CoroutineTestRule(
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    val testCoroutineDispatchers = AppCoroutineDispatchers(testDispatcher, testDispatcher)

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        testDispatcher.runBlockingTest(block)
    }

}