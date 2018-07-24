package ru.weblokos.mtckorovi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;

/**
 * Created by gravitas on 14.07.2018.
 */

public class CowListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<CowFilled>> mObservableCowsFilled;

    public CowListViewModel(Application application) {
        super(application);

        mObservableCowsFilled = new MediatorLiveData<>();

        mObservableCowsFilled.setValue(null);

        LiveData<List<CowFilled>> cowsFilled = ((App) application).getRepository().getCowsFilled();
        mObservableCowsFilled.addSource(cowsFilled, mObservableCowsFilled::setValue);

    }

    public LiveData<List<CowFilled>> getCowsFilled() {
        return mObservableCowsFilled;
    }
}
