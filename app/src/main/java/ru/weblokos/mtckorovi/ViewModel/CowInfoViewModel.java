package ru.weblokos.mtckorovi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;
import java.util.List;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.DB.Converter.DateConverter;
import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.ColorEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;
import ru.weblokos.mtckorovi.DataRepository;
import ru.weblokos.mtckorovi.UI.CowInfoCallback;

public class CowInfoViewModel extends AndroidViewModel {

    private final LiveData<CowFilled> mObservableCow;
    private final LiveData<List<BreedEntity>> mObservableBreeds;
    private final LiveData<List<ColorEntity>> mObservableColors;
    private final LiveData<List<CowFilled>> mObservableCows;

    public ObservableArrayList<String> breeds = new ObservableArrayList<>();
    public ObservableField<CowFilled> cow = new ObservableField<>();
    public ObservableField<String> cowBirthday = new ObservableField<>();

    private final int mCowId;
    private final CowInfoCallback mListener;


    public CowInfoViewModel(@NonNull Application application, DataRepository repository,
                            final int cowId,
                            CowInfoCallback listener) {
        super(application);
        mCowId = cowId;
        mObservableBreeds = repository.getBreeds();
        mObservableColors = repository.getColors();
        mObservableCows = repository.getCowsFilledWithoutOne(cowId);
        mObservableCow = repository.loadCow(mCowId);
        mListener = listener;
    }

    public LiveData<List<ColorEntity>> getObservableColors() {
        return mObservableColors;
    }

    public LiveData<List<BreedEntity>> getObservableBreeds() {
        return mObservableBreeds;
    }

    public LiveData<CowFilled> getObservableCow() {
        return mObservableCow;
    }

    public LiveData<List<CowFilled>> getObservableAllCows() {
        return mObservableCows;
    }

    public void setCow(CowFilled cow) {
        this.cow.set(cow);
        cowBirthday.set(DateConverter.dateToString(cow.getBirthday()));
    }

    public void saveCow() {
        ((App) getApplication()).getExecutors().diskIO().execute(() -> { // заполнение базы тестовыми данными
            ((App) getApplication()).getRepository().updateCow(cow.get().getCow());
        });
    }

    public void changeDate() {
        mListener.onChangeDateClick();
    }

    public void showData() {
        mListener.onShowDataClick();
    }

    public void setCowBirthday(Date date) {
        cow.get().setBirthday(date);
        cowBirthday.set(DateConverter.dateToString(cow.get().getBirthday()));
    }

    public String getBirthday() {
        return cowBirthday.get() != null ? cowBirthday.get() : "" ;
    }

    @BindingAdapter("app:onClick")
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runnable.run();
            }
        });
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mCowId;

        private final DataRepository mRepository;

        private final CowInfoCallback mListener;

        public Factory(@NonNull Application application, int cowId, CowInfoCallback listener) {
            mApplication = application;
            mCowId = cowId;
            mRepository = ((App) application).getRepository();
            mListener = listener;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CowInfoViewModel(mApplication, mRepository, mCowId, mListener);
        }
    }
}
