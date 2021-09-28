package com.app.githubuser.views

import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.app.githubuser.R

class CustomDialog {
    fun error(context: Context, onRefresh: () -> Unit) {
        val dialog = AlertDialog.Builder(context)
            .setView(R.layout.error_dialog)
            .show()

        val btnRefresh = dialog.findViewById<Button>(R.id.btnRefresh)
        btnRefresh?.setOnClickListener {
            onRefresh()
            dialog.dismiss()
        }
    }
}