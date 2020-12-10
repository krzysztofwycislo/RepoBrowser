package pl.handsome.club.repobrowser.util

import android.util.Log


fun Any.logError(throwable: Throwable) {
    Log.e(this::class.simpleName, throwable.message, throwable)
}