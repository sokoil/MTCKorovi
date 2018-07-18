package ru.weblokos.mtckorovi.UI;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by gravitas on 14.07.2018.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
