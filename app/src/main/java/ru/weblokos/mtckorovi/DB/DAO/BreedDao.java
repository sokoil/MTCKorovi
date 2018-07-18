package ru.weblokos.mtckorovi.DB.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;

/**
 * Created by gravitas on 14.07.2018.
 */

@Dao
public interface BreedDao {

    @Query("SELECT * FROM breeds")
    LiveData<List<BreedEntity>> loadAllBreeds();

    @Query("SELECT * FROM breeds")
    List<BreedEntity> loadAllBreedsSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BreedEntity> breeds);

    @Query("select * from breeds where id = :breed")
    BreedEntity loadBreedSync(int breed);
}
