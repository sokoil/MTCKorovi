package ru.weblokos.mtckorovi.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.ViewModel.CowDataListViewModel;
import ru.weblokos.mtckorovi.databinding.ActivityCowDataListBinding;

public class CowDataListActivity extends AppCompatActivity implements CowDataListCallback {

    public static final String TAG = "CowDataListViewModel";
    ActivityCowDataListBinding binding;
    private CowDataAdapter mCowDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cow_data_list);
        mCowDataAdapter = new CowDataAdapter();
        binding.cowDataList.setAdapter(mCowDataAdapter);

        CowDataListViewModel.Factory factory = new CowDataListViewModel.Factory(
                getApplication(), getIntent().getExtras().getInt("cow"),
                this);
        final CowDataListViewModel viewModel = ViewModelProviders.of(this, factory).get(CowDataListViewModel.class);
        binding.setCowDataListViewModel(viewModel);
        subscribeUi(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.cowDataList.getRecycledViewPool().clear();
        mCowDataAdapter.notifyDataSetChanged();
    }

    private void subscribeUi(final CowDataListViewModel viewModel) {
        viewModel.getCowData().observe(this, new Observer<List<CowDataEntity>>() {
            @Override
            public void onChanged(@Nullable List<CowDataEntity> myCowData) {
                if (myCowData != null) {
                    binding.setIsLoading(false);
                    mCowDataAdapter.setCowDataList(myCowData);
                } else {
                    binding.setIsLoading(true);
                }
                binding.executePendingBindings();
            }
        });
    }

    @Override
    public void onAddClick() {
        startActivity(new Intent(this, CowDataActivity.class)
                .putExtra("cow", getIntent().getExtras().getInt("cow")));
    }

    @Override
    public void onShowChartClick() {
        startActivity(new Intent(this, CowDataChartActivity.class)
                .putExtra("cow", getIntent().getExtras().getInt("cow")));
    }
}
