package com.example.wrestlingmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

//Utilities is a Class used to build alerts that pop up when things dont go the way they are supposed to go.
public class Utilities {
    public static void showAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog,id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }


}
