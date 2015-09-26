package apdroid.clinica.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ANTONIO on 23/09/2015.
 */
public class Utiles {

    public static String cambiarFormatoFecha( String fecha, String originFormat, String finalFormat){
        String resp = "";

        if (fecha!=null && !"".equals(fecha)){
            SimpleDateFormat sdfOrigin = new SimpleDateFormat(originFormat);
            SimpleDateFormat sdfFinal = new SimpleDateFormat(finalFormat);

            try {
                Date date = sdfOrigin.parse(fecha);
                resp = sdfFinal.format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return  resp;
    }


    public static String obtenerValorSharedPreference(Activity activity, String keyValor){
        String resp = "";
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        resp = sharedPref.getString(keyValor, "");
        return resp;
    }

    public static int obtenerValorSharedPreferenceInt(Activity activity, String keyValor){
        int resp = -1;
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        resp = sharedPref.getInt(keyValor, -1);
        return resp;
    }

}
