package pl.handsome.club.repobrowser.util

import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.*

class DebounceTextWatcher(
    private val coroutineScope: CoroutineScope,
    private val onTextChanged: (String) -> Unit,
    private val debounceDelayMillis: Long = 300
) : TextWatcher {

    private var debounceJob: Job? = null


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val text = s.toString().trim()

        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(debounceDelayMillis)
            onTextChanged(text)
        }
    }

    override fun afterTextChanged(s: Editable?) = Unit
}