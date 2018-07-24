package ru.weblokos.mtckorovi.DB.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.weblokos.mtckorovi.DB.Entity.CowEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;

/**
 * Created by gravitas on 14.07.2018.
 */

@Dao
public interface CowDao {

    @Update
    void update(CowEntity cow);

    @Query("SELECT * FROM cows")
    LiveData<List<CowEntity>> loadAllCows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CowEntity> cows);

    @Query("select cows.*, color_value, breed_value from cows JOIN colors ON cows.color = colors.id JOIN breeds ON cows.breed = breeds.id")
    LiveData<List<CowFilled>> loadAllCowsWithProperties();

    @Query("select cows.*, color_value, breed_value from cows JOIN colors ON cows.color = colors.id JOIN breeds ON cows.breed = breeds.id where not cows.id = :cow")
    LiveData<List<CowFilled>> loadAllCowsWithPropertiesWithoutOne(int cow);

    @Query("select cows.*, color_value, breed_value from cows JOIN colors ON cows.color = colors.id JOIN breeds ON cows.breed = breeds.id where cows.id = :cow")
    LiveData<CowFilled> loadCowWithProperties(int cow);
}