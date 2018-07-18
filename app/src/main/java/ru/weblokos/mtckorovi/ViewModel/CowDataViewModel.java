package ru.weblokos.mtckorovi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.database.Observable;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import java.util.Date;
import java.util.List;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.BR;
import ru.weblokos.mtckorovi.DB.Converter.DateConverter;
import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.ColorEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;
import ru.weblokos.mtckorovi.DataRepository;
import ru.weblokos.mtckorovi.UI.CowDataCallback;
import ru.weblokos.mtckorovi.UI.CowInfoCallback;

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
