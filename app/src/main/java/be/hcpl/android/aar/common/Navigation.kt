package be.hcpl.android.aar.common

import android.content.Context
import android.content.Intent
import be.hcpl.android.aar.mvc.MvcActivity
import be.hcpl.android.aar.mvi.MviActivity
import be.hcpl.android.aar.mvp.MvpActivity
import be.hcpl.android.aar.mvvm.MvvmActivity

fun Context.navigate(destination: Navigation) {
    when (destination) {
        Navigation.MVC -> startActivity(Intent(this, MvcActivity::class.java))
        Navigation.MVP -> startActivity(Intent(this, MvpActivity::class.java))
        Navigation.MVVM -> startActivity(Intent(this, MvvmActivity::class.java))
        Navigation.MVI -> startActivity(Intent(this, MviActivity::class.java))
    }
}

enum class Navigation {
    MVC,
    MVP,
    MVVM,
    MVI,
}