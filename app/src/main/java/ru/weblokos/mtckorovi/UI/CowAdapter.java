package ru.weblokos.mtckorovi.UI;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import ru.weblokos.mtckorovi.Model.Cow;
import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.databinding.CowItemBinding;

/**
 * Created by gravitas on 14.07.2018.
 */

public class CowAdapter extends RecyclerView.Adapter<CowAdapter.CowViewHolder> {

    List<? extends Cow> mCowList;

    @Nullable
    private final CowClickCallback mCowClickCallback;

    public CowAdapter(@Nullable CowClickCallback clickCallback) {
        mCowClickCallback = clickCallback;
    }

    public void setCowList(final List<? extends Cow> cowList) {
        if (mCowList == null) {
            mCowList = cowList;
            notifyItemRangeInserted(0, cowList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCowList.size();
                }

                @Override
                public int getNewListSize() {
                    return cowList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCowList.get(oldItemPosition).getNumber() ==
                            cowList.get(newItemPosition).getNumber();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Cow newCow = cowList.get(newItemPosition);
                    Cow oldCow = mCowList.get(oldItemPosition);
                    return newCow.getNumber() == oldCow.getNumber();
                }
            });
            mCowList = cowList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public CowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CowItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.cow_item, parent,
                        false);
        binding.setCallback(mCowClickCallback);
        return new CowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CowViewHolder holder, int position) {
        holder.binding.setCow(mCowList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCowList == null ? 0 : mCowList.size();
    }

    static class CowViewHolder extends RecyclerView.ViewHolder {

        final CowItemBinding binding;

        public CowViewHolder(CowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}