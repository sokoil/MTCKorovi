package ru.weblokos.mtckorovi.DB;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.ColorEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowEntity;

/**
 * Created by gravitas on 14.07.2018.
 */

public class DataGenerator { // возможные свойства коровы

    public static final String[] arr_breed = new String[]{ // возможные породы коров
            "Голштинская",
            "Симменталка",
            "Айширская",
            "Джерсейская",
            "Швицкая",
            "Холмогорская",
            "Ярославская",
            "Костромская",
            "Голштино-фризская",
            "Гернзейская",
            "Айрширская"
    };

    public static final int[] arr_colors = new int[]{ // возможные цвета коров. список цветов реальных коров найти не смог
            Color.RED,
            Color.BLACK,
            Color.BLUE,
            Color.GRAY,
            Color.GREEN,
            Color.WHITE,
            Color.YELLOW,
            Color.LTGRAY
    };

    public static List<BreedEntity> generateBreeds() {
        List<BreedEntity> breeds = new ArrayList<>();
        for (int i = 0; i < arr_breed.length; i++) {
            breeds.add(new BreedEntity(0, arr_breed[i]));
        }
        return breeds;
    }

    public static List<ColorEntity> generateColors() {
        List<ColorEntity> colors = new ArrayList<>();
        for (int i = 0; i < arr_colors.length; i++) {
            colors.add(new ColorEntity(0, arr_colors[i]));
        }
        return colors;
    }

    public static List<CowEntity> generateCows() {
        List<CowEntity> cows = new ArrayList<>();
        Random rnd = new Random();
        long min_time = (new Date().getTime()) - 1000*60*60*24*30*6; // для генерации коров которым минимум 6 месяцев
        for (int i = 0; i < 10; i++) {
            cows.add(new CowEntity(
                    i+1,
                    rnd.nextInt(arr_breed.length)+1,
                    rnd.nextInt(arr_colors.length)+1,
                    new Date(min_time - ((long)rnd.nextInt(10))*1000*60*60*24*30) // добавляем к минимальному возрасту коровы (6 месяцев) еще rand(10) месяцев
            ));
        }
        return cows;
    }
}
