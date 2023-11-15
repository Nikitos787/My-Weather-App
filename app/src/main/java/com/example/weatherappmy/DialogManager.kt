package com.example.weatherappmy

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {
    fun locationSettingDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enable location?")
        dialog.setMessage("Location disabled. Do you want to enable location?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByName(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val editTextForCityName =EditText(context)
        builder.setView(editTextForCityName)
        val dialog = builder.create()
        dialog.setTitle("City name:")

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            listener.onClick(editTextForCityName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun errorMessage(context: Context, e: String) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(e)
            .setPositiveButton(
                "Close") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    interface Listener {
        fun onClick(name : String?)
    }
}