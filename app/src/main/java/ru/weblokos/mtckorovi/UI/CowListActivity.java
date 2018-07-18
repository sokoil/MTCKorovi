package ru.weblokos.mtckorovi.UI;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.weblokos.mtckorovi.DB.Entity.CowFilled;
import ru.weblokos.mtckorovi.Model.Cow;
import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.ViewModel.CowListViewModel;
import ru.weblokos.mtckorovi.databinding.ActivityListBinding;

public class CowListActivity extends AppCompatActivity {

    public static final String TAG = "CowListViewModel";
    ActivityListBinding binding;
    private CowAdapter mCowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        mCowAdapter = new CowAdapter(mCowClickCallback);
        binding.cowsList.setAdapter(mCowAdapter);

        final CowListViewModel viewModel = ViewModelProviders.of(this).get(CowListViewModel.class);
        subscribeUi(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.cowsList.getRecycledViewPool().clear();
        mCowAdapter.notifyDataSetChanged();
    }

    private void subscribeUi(final CowListViewModel viewModel) {
        viewModel.getCowsFilled().observe(this, new Observer<List<CowFilled>>() {
            @Override
            public void onChanged(@Nullable List<CowFilled> myCows) {
                if (myCows != null) {
                    binding.setIsLoading(false);
                    mCowAdapter.setCowList(myCows);
                } else {
                    binding.setIsLoading(true);
                }
                binding.executePendingBindings();
            }
        });
    }

    private final CowClickCallback mCowClickCallback = new CowClickCallback() {
        @Override
        public void onClick(Cow cow) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(new Intent(CowListActivity.this, CowInfoActivity.class).putExtra("cow", cow.getNumber()));
            }
        }
    };

}
