package be.hcpl.android.aar.common

import android.content.Context
import android.content.Intent
import be.hcpl.android.aar.mvc.MvcActivity
import be.hcpl.android.aar.mvi.MviActivity
import be.hcpl.android.aar.mvp.MvpActivity
import be.hcpl.android.aar.mvvm.MvvmActivity

fun Context.navigate(destination: String) {
    when (destination) {
        "MVC" -> startActivity(Intent(this, MvcActivity::class.java))
        "MVP" -> startActivity(Intent(this, MvpActivity::class.java))
        "MVVM" -> startActivity(Intent(this, MvvmActivity::class.java))
        "MVI" -> startActivity(Intent(this, MviActivity::class.java))
    }
}