package com.upax.moviesapp.utils

import android.app.AlertDialog
import android.content.Context
import com.upax.moviesapp.R


fun showSimpleAlert(context: Context, msg: String) {
    AlertDialog.Builder(context)
        .setMessage(msg)
        .setIcon(context.resources.getDrawable(R.drawable.ic_baseline_check_24))
        .setPositiveButton("OK") { dialog, id -> }
        .show()
}


