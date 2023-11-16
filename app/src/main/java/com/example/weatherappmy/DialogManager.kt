package com.example.weatherappmy

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {
    fun locationSettingDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.enable_location))
        dialog.setMessage(context.getString(R.string.location_disabled_do_you_want_to_enable_location))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)) { _, _ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.no)) { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByName(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val editTextForCityName =EditText(context)
        builder.setView(editTextForCityName)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.city_name))

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.resources.getString(R.string.ok)) { _, _ ->
            listener.onClick(editTextForCityName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.resources.getString(R.string.no)) { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun errorMessage(context: Context, e: String) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.error))
            .setMessage(e)
            .setPositiveButton(
                context.getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    interface Listener {
        fun onClick(name : String?)
    }
}
