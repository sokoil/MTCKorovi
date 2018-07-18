package ru.weblokos.mtckorovi.DB.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.weblokos.mtckorovi.DB.Entity.ColorEntity;

/**
 * Created by gravitas on 14.07.2018.
 */

@Dao
public interface ColorDao {

    @Query("SELECT * FROM colors")
    LiveData<List<ColorEntity>> loadAllColors();

    @Query("SELECT * FROM colors")
    List<ColorEntity> loadAllColorsSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ColorEntity> colors);

    @Query("select * from colors where id = :color")
    ColorEntity loadColorSync(int color);

    @Query("select * from colors where id = :color")
    LiveData<ColorEntity> loadColor(int color);
}
