package ru.weblokos.mtckorovi.UI;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ru.weblokos.mtckorovi.Model.Cow;
import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.databinding.CowSpinnerItemBinding;

public class SpinnerCowAdapter extends ArrayAdapter<Cow>
{
    private int itemLayoutResourceId;
    List<? extends Cow> mCowList;
    private LayoutInflater inflater;

    public SpinnerCowAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void setColorList(final List<? extends Cow> cowList) {
        if (mCowList == null) {
            mCowList = cowList;
        } else {
            mCowList = cowList;
        }
    }

    @Override
    public int getCount()
    {
        return mCowList != null ? mCowList.size() : 0;
    }

    @Override
    public Cow getItem(int position)
    {
        return mCowList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ItemViewHolder holder;

        CowSpinnerItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cow_spinner_item, parent, false);
        itemBinding.setCow(mCowList.get(position));

        holder = new ItemViewHolder(itemBinding);
        holder.view = itemBinding.getRoot().findViewById(R.id.lay);
        holder.view.setTag(holder);
        return holder.view;
    }

    static class ItemViewHolder
    {
        private View view;

        ItemViewHolder(CowSpinnerItemBinding binding)
        {
            this.view = binding.getRoot();
        }
    }}
