package se.chalmers.eda397.pairprogramming.core;

/**
 * Created by Mark on 5/7/2015.
 */
import android.app.Application;
import android.content.Context;

public class applicationContextProvider extends Application {

    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }

}
