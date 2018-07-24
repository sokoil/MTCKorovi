package ru.weblokos.mtckorovi.ViewModel;

import android.app.Application;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.UI.CowDataCallback;

public class CowDataViewModel extends BaseObservable {

    public CowDataEntity cowData;

    private final int mCowId;
    private final CowDataCallback mListener;
    private transient PropertyChangeRegistry callbacks;
    private final Application application;


    public CowDataViewModel(@NonNull Application application,
                            final int cowId,
                            CowDataCallback listener) {
        this.application = application;
        mCowId = cowId;
        cowData = new CowDataEntity(mCowId);
        mListener = listener;
    }

    public void saveCowData() {
        cowData.setData(mListener.getData());
        ((App) application).getExecutors().diskIO().execute(() -> { // заполнение базы тестовыми данными
            ((App) application).getRepository().addCowData(cowData);
        });
        mListener.onSave();
    }

    public void changeDate() {
        mListener.onDateChange();
    }

    public void setCowDataDate(Date date) {
        cowData.setDate(date);
        notifyChange();
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

    public void setCommuteType(int type) {
        cowData.setType(type);
    }

}
