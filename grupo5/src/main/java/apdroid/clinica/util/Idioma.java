package apdroid.clinica.util;

import java.util.Locale;

/**
 * Created by Edinson on 21/09/2015.
 */
public class Idioma {
    public void cambiaIdioma(String idioma , android.content.Context context){
        String abrev;
        if(idioma.equals("Español")){
            abrev ="es";
        }else{
            abrev ="en";
        }

        Locale.setDefault(new Locale(abrev));
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = new Locale(abrev);
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());

    }

    public Idioma() {
    }
}
