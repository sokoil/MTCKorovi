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

import ru.weblokos.mtckorovi.Model.Color;
import ru.weblokos.mtckorovi.R;
import ru.weblokos.mtckorovi.databinding.ColorSpinnerItemBinding;

public class SpinnerColorAdapter extends ArrayAdapter<Color>
{
    private int itemLayoutResourceId;
    List<? extends Color> mColorList;
    private LayoutInflater inflater;

    public SpinnerColorAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void setColorList(final List<? extends Color> colorList) {
        if (mColorList == null) {
            mColorList = colorList;
        } else {
            mColorList = colorList;
        }

    }

    @Override
    public int getCount()
    {
        return mColorList != null ? mColorList.size() : 0;
    }

    @Override
    public Color getItem(int position)
    {
        return mColorList.get(position);
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

        ColorSpinnerItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.color_spinner_item, parent, false);
        itemBinding.setColor(mColorList.get(position));

        holder = new ItemViewHolder(itemBinding);
        holder.view = itemBinding.getRoot().findViewById(R.id.lay);
        holder.view.setTag(holder);
        return holder.view;
    }

    static class ItemViewHolder
    {
        private View view;

        ItemViewHolder(ColorSpinnerItemBinding binding)
        {
            this.view = binding.getRoot();
        }
    }}
