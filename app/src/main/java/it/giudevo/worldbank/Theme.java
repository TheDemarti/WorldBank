package it.giudevo.worldbank;

import android.app.Activity;

public class Theme {
    private static int sTheme;
    final static int THEME_FIRST = 0;
    final static int THEME_SECOND = 1;

    //modifica il tema dell'activity e ricrea il tema senza rilanciare l'activity
    static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.recreate();
    }

    //modifica il tema dell'activity in accordo con la configurazione
    static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_FIRST:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_SECOND:
                activity.setTheme(R.style.Theme_AppCompat_DayNight);
                break;
        }
    }
}
