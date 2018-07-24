package ru.weblokos.mtckorovi;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import ru.weblokos.mtckorovi.DB.DataBase;
import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.ColorEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;

/**
 * Created by gravitas on 14.07.2018.
 */

public class DataRepository {
    private static DataRepository sInstance;

    private final DataBase mDatabase;
    private MediatorLiveData<List<CowEntity>> mObservableCows;
    private MediatorLiveData<List<ColorEntity>> mObservableColors;
    private MediatorLiveData<List<BreedEntity>> mObservableBreeds;
    private MediatorLiveData<List<CowFilled>> mObservableCowsFilled;

    private DataRepository(final DataBase database) {
        mDatabase = database;
        mObservableCows = new MediatorLiveData<>();
        mObservableColors = new MediatorLiveData<>();
        mObservableBreeds = new MediatorLiveData<>();
        mObservableCowsFilled = new MediatorLiveData<>();

        mObservableCows.addSource(mDatabase.cowDao().loadAllCows(),
                cowEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableCows.postValue(cowEntities);
                    }
                });
        mObservableColors.addSource(mDatabase.colorDao().loadAllColors(),
                colorEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableColors.postValue(colorEntities);
                    }
                });
        mObservableBreeds.addSource(mDatabase.breedDao().loadAllBreeds(),
                breedEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableBreeds.postValue(breedEntities);
                    }
                });
        mObservableCowsFilled.addSource(mDatabase.cowDao().loadAllCowsWithProperties(),
                cowEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableCowsFilled.postValue(cowEntities);
                    }
                });
    }

    public static DataRepository getInstance(final DataBase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<CowEntity>> getCows() {
        return mObservableCows;
    }

    public LiveData<List<ColorEntity>> getColors() {
        return mObservableColors;
    }

    public LiveData<List<BreedEntity>> getBreeds() {
        return mObservableBreeds;
    }

    public LiveData<List<CowFilled>> getCowsFilled() {
        return mObservableCowsFilled;
    }

    public LiveData<CowFilled> loadCow(final int cowId) {
        return mDatabase.cowDao().loadCowWithProperties(cowId);
    }

    public LiveData<List<CowFilled>> getCowsFilledWithoutOne(final int cowId) {
        return mDatabase.cowDao().loadAllCowsWithPropertiesWithoutOne(cowId);
    }

    public void updateCow(final CowEntity cowEntity) {
        mDatabase.cowDao().update(cowEntity);
    }

    public LiveData<List<CowDataEntity>> loadCowData(final int cowId) {
        return mDatabase.cowDataDao().loadDataFromCow(cowId);
    }

    public void addCowData(CowDataEntity cowDataEntity) {
        mDatabase.cowDataDao().insert(cowDataEntity);
    }
}
