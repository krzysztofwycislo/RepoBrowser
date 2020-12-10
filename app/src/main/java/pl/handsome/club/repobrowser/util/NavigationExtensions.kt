package pl.handsome.club.repobrowser.util

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import pl.handsome.club.repobrowser.R


val defaultAnimationOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.nav_enter_anim)
    .setExitAnim(R.anim.nav_exit_anim)
    .setPopEnterAnim(R.anim.nav_pop_enter_anim)
    .setPopExitAnim(R.anim.nav_pop_exit_anim)
    .build()

fun NavController.safeNavigate(@IdRes resId: Int) {
    currentDestination?.getAction(resId)?.let { navigate(resId, null, defaultAnimationOptions) }
}