package com.app.githubuser.views

import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.app.githubuser.R

class CustomDialog {
    fun error(context: Context) {
        val dialog = AlertDialog.Builder(context)
            .setView(R.layout.error_dialog)
            .show()

        val btnOk = dialog.findViewById<Button>(R.id.btnOk)
        btnOk?.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun warning(context: Context) {
        val dialog = AlertDialog.Builder(context)
            .setView(R.layout.warning_dialog)
            .show()

        val btnOk = dialog.findViewById<Button>(R.id.btnOk)
        btnOk?.setOnClickListener {
            dialog.dismiss()
        }
    }
}