package ru.weblokos.mtckorovi.UI;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.ViewModel.CowDataViewModel;
import ru.weblokos.mtckorovi.databinding.ActivityCowDataItemBinding;

public class CowDataActivity extends AppCompatActivity implements CowDataCallback {

    public static final String TAG = "CowDataViewModel";
    ActivityCowDataItemBinding binding;
    private CowDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cow_data_item);
        viewModel = new CowDataViewModel(getApplication(),
                getIntent().getExtras().getInt("cow"), this);
        binding.setCowDataViewModel(viewModel);
    }

    DatePickerDialog.OnDateSetListener dateCallBack = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            Date chosenDate = cal.getTime();
            viewModel.setCowDataDate(chosenDate);
            binding.notifyChange();
        }
    };

    @Override
    public void onSave() {
        finish();
    }

    @Override
    public void onDateChange() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(viewModel.cowData.getDate());
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateCallBack, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public double getData() {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            return format.parse(binding.data.getText().toString()).doubleValue();
        } catch (ParseException e) {
            return 0;
        }
    }
}
