package ru.weblokos.mtckorovi.UI;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.weblokos.mtckorovi.Model.CowData;
import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.databinding.CowDataItemBinding;

/**
 * Created by gravitas on 14.07.2018.
 */

public class CowDataAdapter extends RecyclerView.Adapter<CowDataAdapter.CowDataViewHolder> {

    List<? extends CowData> mCowDataList;


    public CowDataAdapter() {

    }

    public void setCowDataList(final List<? extends CowData> CowDataList) {
        if (mCowDataList == null) {
            mCowDataList = CowDataList;
            notifyItemRangeInserted(0, CowDataList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCowDataList.size();
                }

                @Override
                public int getNewListSize() {
                    return CowDataList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCowDataList.get(oldItemPosition).getId() ==
                            CowDataList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CowData newCowData = CowDataList.get(newItemPosition);
                    CowData oldCowData = mCowDataList.get(oldItemPosition);
                    return newCowData.getId() == oldCowData.getId();
                }
            });
            mCowDataList = CowDataList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public CowDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CowDataItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.cow_data_item, parent,
                        false);
        return new CowDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CowDataViewHolder holder, int position) {
        holder.binding.setCowData(mCowDataList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCowDataList == null ? 0 : mCowDataList.size();
    }

    static class CowDataViewHolder extends RecyclerView.ViewHolder {

        final CowDataItemBinding binding;

        public CowDataViewHolder(CowDataItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}