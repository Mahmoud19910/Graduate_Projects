package dev.mah.nassa.gradu_ptojects.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LanguageUtils {

    public static void changeLanguage(Context context, String languageCode) {
        Locale newLocale = new Locale(languageCode);
        Locale.setDefault(newLocale);

        Resources resources = ((Activity)context).getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = newLocale;
        ((Activity)context).getResources().updateConfiguration(configuration, ((Activity)context).getResources().getDisplayMetrics());
    }
}
