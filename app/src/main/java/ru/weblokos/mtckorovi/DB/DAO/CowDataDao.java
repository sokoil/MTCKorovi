package ru.weblokos.mtckorovi.DB.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.weblokos.mtckorovi.DB.Entity.BreedEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowEntity;
import ru.weblokos.mtckorovi.DB.Entity.CowFilled;
import ru.weblokos.mtckorovi.Model.CowData;

/**
 * Created by gravitas on 14.07.2018.
 */

@Dao
public interface CowDataDao {

    @Update
    void update(CowDataEntity cowDataEntity);

    @Query("SELECT * FROM cow_data WHERE cow_id = :cow")
    LiveData<List<CowDataEntity>> loadDataFromCow(int cow);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CowDataEntity cowDataEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CowDataEntity> cowDataEntityList);

}