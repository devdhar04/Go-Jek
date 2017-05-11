package com.android.gojek.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.android.gojek.R;

/**
 * Created by dev on 07/05/17.
 */

public class Utils {

    private static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean isNetworkavailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isEmailValid(String email)
    {
        if(email.matches(emailPattern))
        {
            return true;
        }
    return false;
    }

    public static AlertDialog showAlertDialog(String message,Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setCancelable(true)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         dialog.dismiss();
                    }
                });
        AlertDialog d = builder.create();
        d.setTitle("Error !");
        d.show();
    return d ;
    }

}
