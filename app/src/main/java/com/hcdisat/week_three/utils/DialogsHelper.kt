package com.hcdisat.week_three.utils

import android.app.AlertDialog
import android.content.Context
import com.hcdisat.week_three.R

/**
 * make a dialog alert
 */
fun messageDialog(context: Context, title: String = "", message: String = ""): AlertDialog.Builder {
    return AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setIcon(R.drawable.ic_broken_image)
        .setPositiveButton("OK") { _, _ ->
            //
        }
}