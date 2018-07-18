package ru.weblokos.mtckorovi.DB;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.List;

import ru.weblokos.mtckorovi.AppExecutors;
import ru.weblokos.mtckorovi.DB.Converter.DateConverter;
import ru.weblokos.mtckorovi.DB.DAO.BreedDao;
import ru.weblokos.mtckorovi.DB.DAO.ColorDao;
import ru.weblokos.mtckorovi.DB.DAO.CowDao;
import ru.weblokos.mtckorovi.DB.DAO.CowDataDao;
import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.ColorEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowEntity;

/**
 * Created by gravitas on 14.07.2018.
 */

@Database(entities = {CowEntity.class, BreedEntity.class, ColorEntity.class, CowDataEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class DataBase extends RoomDatabase {

    private static DataBase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    @VisibleForTesting
    public static final String DATABASE_NAME = "mtc-cows-db";

    public abstract CowDao cowDao();
    public abstract BreedDao breedDao();
    public abstract ColorDao colorDao();
    public abstract CowDataDao cowDataDao();

    public static DataBase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (DataBase.class) {
                if (sInstance == null) {
                    sInstance = create(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context);
                }
            }
        }
        return sInstance;
    }

    private static DataBase create(final Context context, final AppExecutors executors) {
        return Room.databaseBuilder(context.getApplicationContext(), DataBase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.diskIO().execute(() -> { // заполнение базы тестовыми данными
                    DataBase database = DataBase.getInstance(context, executors);
                    insertData(database, DataGenerator.generateBreeds(), DataGenerator.generateColors(), DataGenerator.generateCows());
                    database.setDatabaseCreated();
                });
            }
        }).build();
    }

    private static void insertData(final DataBase database,
                                   final List<BreedEntity> breeds,
                                   final List<ColorEntity> colors,
                                   final List<CowEntity> cows) {
        database.runInTransaction(() -> {
            database.breedDao().insertAll(breeds);
            database.colorDao().insertAll(colors);
            database.cowDao().insertAll(cows);
        });
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(Boolean.valueOf(true));
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
