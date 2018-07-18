package ru.weblokos.mtckorovi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import java.util.List;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.ColorEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;

/**
 * Created by gravitas on 14.07.2018.
 */

public class CowListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<CowEntity>> mObservableCows;
    private final MediatorLiveData<List<ColorEntity>> mObservableColors;
    private final MediatorLiveData<List<BreedEntity>> mObservableBreeds;
    private final MediatorLiveData<List<CowFilled>> mObservableCowsFilled;

    public CowListViewModel(Application application) {
        super(application);

        mObservableCows = new MediatorLiveData<>();
        mObservableColors = new MediatorLiveData<>();
        mObservableBreeds = new MediatorLiveData<>();
        mObservableCowsFilled = new MediatorLiveData<>();


        mObservableCows.setValue(null);
        mObservableColors.setValue(null);
        mObservableBreeds.setValue(null);
        mObservableCowsFilled.setValue(null);

        LiveData<List<BreedEntity>> breeds = ((App) application).getRepository().getBreeds();
        LiveData<List<ColorEntity>> colors = ((App) application).getRepository().getColors();
        LiveData<List<CowEntity>> cows = ((App) application).getRepository().getCows();
        LiveData<List<CowFilled>> cowsFilled = ((App) application).getRepository().getCowsFilled();

        mObservableBreeds.addSource(breeds, mObservableBreeds::setValue);
        mObservableColors.addSource(colors, mObservableColors::setValue);
        mObservableCows.addSource(cows, mObservableCows::setValue);
        mObservableCowsFilled.addSource(cowsFilled, mObservableCowsFilled::setValue);

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
}
