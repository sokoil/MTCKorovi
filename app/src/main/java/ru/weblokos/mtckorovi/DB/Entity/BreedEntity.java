package ru.weblokos.mtckorovi.DB.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ru.weblokos.mtckorovi.Model.Breed;

@Entity(tableName = "breeds")
public class BreedEntity implements Breed { // Породы и масти

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "breed_value")
    private String breed; // порода \ масть

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getBreed() {
        return breed;
    }

    public BreedEntity(int id, String breed) {
        this.id = id;
        this.breed = breed;
    }
}
