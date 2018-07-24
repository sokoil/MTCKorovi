package ru.weblokos.mtckorovi.UI;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;
import ru.weblokos.mtckorovi.Model.Cow;
import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.ViewModel.CowInfoViewModel;
import ru.weblokos.mtckorovi.databinding.ActivityCowInfoBinding;

public class CowInfoActivity extends AppCompatActivity implements CowInfoCallback {

    public static final String TAG = "CowInfoViewModel";
    ActivityCowInfoBinding binding;
    private SpinnerColorAdapter mColorAdapter;
    private SpinnerCowAdapter mMotherAdapter;
    private SpinnerCowAdapter mFatherAdapter;
    private CowInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cow_info);
        CowInfoViewModel.Factory factory = new CowInfoViewModel.Factory(
                getApplication(), getIntent().getExtras().getInt("cow"),
                this);
        viewModel = ViewModelProviders.of(this, factory).get(CowInfoViewModel.class);
        binding.setCowInfoViewModel(viewModel);
        setUpUi(viewModel);
        subscribeUi(viewModel);
    }

    private void setUpUi(final CowInfoViewModel viewModel) {
        mColorAdapter = new SpinnerColorAdapter(this, R.layout.color_spinner_item);
        mMotherAdapter = new SpinnerCowAdapter(this, R.layout.cow_spinner_item);
        mFatherAdapter = new SpinnerCowAdapter(this, R.layout.cow_spinner_item);
        binding.color.setAdapter(mColorAdapter);
        binding.mother.setAdapter(mMotherAdapter);
        binding.father.setAdapter(mFatherAdapter);
        setUpListeners();
    }

    private void setUpListeners() {
        binding.color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(viewModel.cow.get() != null)
                    ((App) CowInfoActivity.this.getApplication()).getExecutors().diskIO().execute(() -> {
                        int color = viewModel.getObservableColors().getValue().get(i).getId();
                        viewModel.cow.get().getCow().setColor(color);
                    });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.mother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(viewModel.cow.get() != null)
                    ((App) CowInfoActivity.this.getApplication()).getExecutors().diskIO().execute(() -> {
                        int mother = i == 0 ? 0 : viewModel.getObservableAllCows().getValue().get(i-1).getId();
                        viewModel.cow.get().getCow().setMother(mother);
                    });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.father.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(viewModel.cow.get() != null)
                    ((App) CowInfoActivity.this.getApplication()).getExecutors().diskIO().execute(() -> {
                        int father = i == 0 ? i : viewModel.getObservableAllCows().getValue().get(i-1).getId();
                        viewModel.cow.get().getCow().setFather(father);
                    });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void updateParents() {
        if(viewModel.cow.get() != null && viewModel.getObservableAllCows().getValue() != null) {
            List<Cow> cows = new ArrayList<>();
            cows.add(new CowFilled(new CowEntity(0, 0, 0, null)));
            for(Cow cow : viewModel.getObservableAllCows().getValue())
                cows.add(cow);

            mMotherAdapter.setColorList(cows);
            mMotherAdapter.notifyDataSetChanged();

            mFatherAdapter.setColorList(cows);
            mFatherAdapter.notifyDataSetChanged();

            if(viewModel.cow.get().getMother() != 0) {
                for (int i = 1; i < cows.size(); i++) {
                    if (cows.get(i).getId() == viewModel.cow.get().getMother()) {
                        binding.mother.setSelection(i);
                    }
                }
            }
            if(viewModel.cow.get().getFather() != 0) {
                for (int i = 1; i < cows.size(); i++) {
                    if (cows.get(i).getId() == viewModel.cow.get().getFather()) {
                        binding.father.setSelection(i);
                    }
                }
            }
        }
    }

    private void subscribeUi(final CowInfoViewModel viewModel) {
        viewModel.getObservableCow().observe(this,
                mCow -> {
                    viewModel.setCow(mCow);
                    if(viewModel.getObservableColors().getValue() != null && viewModel.getObservableCow().getValue() != null) {
                        binding.color.setSelection(viewModel.getObservableCow().getValue().getColor() - 1);
                    }
                    updateParents();
                });
        viewModel.getObservableBreeds().observe(this,
                breedEntities -> {
                    if (breedEntities != null) {
                        viewModel.breeds.clear();
                        for(BreedEntity breed : breedEntities)
                            viewModel.breeds.add(breed.getBreed());
                    }
                });
        viewModel.getObservableColors().observe(this,
                colorEntities -> {
                    if (colorEntities != null) {
                        mColorAdapter.setColorList(colorEntities);
                        mColorAdapter.notifyDataSetChanged();
                        if(viewModel.getObservableCow().getValue() != null)
                            binding.color.setSelection(viewModel.getObservableCow().getValue().getColor()-1);
                    }
                });
        viewModel.getObservableAllCows().observe(this,
                cowFilleds -> {
                    updateParents();
                });
    }

    @Override
    public void onChangeDateClick() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(viewModel.cow.get().getBirthday());
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateCallBack, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onShowDataClick() {
        startActivity(new Intent(this, CowDataListActivity.class)
                .putExtra("cow", getIntent().getExtras().getInt("cow")));
    }

    DatePickerDialog.OnDateSetListener dateCallBack = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            Date chosenDate = cal.getTime();
            viewModel.setCowBirthday(chosenDate);
        }
    };
}
