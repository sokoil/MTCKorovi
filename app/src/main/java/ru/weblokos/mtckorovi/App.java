package ru.weblokos.mtckorovi;

import android.app.Application;

import ru.weblokos.mtckorovi.DB.DataBase;

/**
 * Created by gravitas on 14.07.2018.
 */

public class App extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();

    }

    public AppExecutors getExecutors() {
        return mAppExecutors;
    }

    public DataBase getDatabase() {
        return DataBase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
