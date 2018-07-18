package ru.weblokos.mtckorovi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.DataRepository;
import ru.weblokos.mtckorovi.UI.CowDataListCallback;

/**
 * Created by gravitas on 14.07.2018.
 */

public class CowDataListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<CowDataEntity>> mObservableCowData;
    private final int mCowId;
    private final CowDataListCallback mListener;

    public CowDataListViewModel(@NonNull Application application, DataRepository repository,
                                final int cowId,
                                CowDataListCallback callback) {
        super(application);
        mCowId = cowId;
        mListener = callback;
        mObservableCowData = new MediatorLiveData<>();
        mObservableCowData.setValue(null);
        LiveData<List<CowDataEntity>> datas = repository.loadCowData(mCowId);
        mObservableCowData.addSource(datas, mObservableCowData::setValue);
    }

    public void addData() {
        mListener.onAddClick();
    }

    public void showChart() {
        mListener.onShowChartClick();
    }

    public LiveData<List<CowDataEntity>> getCowData() {
        return mObservableCowData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mCowId;

        private final DataRepository mRepository;

        private final CowDataListCallback mCallback;

        public Factory(@NonNull Application application, int cowId, CowDataListCallback callback) {
            mApplication = application;
            mCowId = cowId;
            mCallback = callback;
            mRepository = ((App) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CowDataListViewModel(mApplication, mRepository, mCowId, mCallback);
        }
    }
}
