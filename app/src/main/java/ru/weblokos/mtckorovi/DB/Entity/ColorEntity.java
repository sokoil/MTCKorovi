package ru.weblokos.mtckorovi.DB.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ru.weblokos.mtckorovi.Model.Color;

@Entity(tableName = "colors")
public class ColorEntity implements Color { // Цвета коров

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "color_value")
    private int color; // цвет

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getColor() {
        return color;
    }

    public ColorEntity(int id, int color) {
        this.id = id;
        this.color = color;
    }
}
