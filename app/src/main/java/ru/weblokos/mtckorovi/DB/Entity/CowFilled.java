package ru.weblokos.mtckorovi.DB.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.graphics.Color;

import java.util.Date;

import ru.weblokos.mtckorovi.Model.Cow;

public class CowFilled implements Cow {
    @Embedded
    private CowEntity cow;

    @ColumnInfo(name = "color_value")
    public int color_value;

    @ColumnInfo(name = "breed_value")
    public String breed_value;

    public CowEntity getCow() {
        return cow;
    }

    public void setCow(CowEntity cow) {
        this.cow = cow;
    }

    public CowFilled(CowEntity cow) {
        this.cow = cow;
    }

    public void setColorValue(int color_value) { // в классе есть лишние методы, по какой-то странной причине без них не компиллилось, пришлось оставить
        this.color_value = color_value;

    }

    public int getColorValue() {
        return color_value;
    }

    public int getId() {
        return cow.getId();
    }

    @Override
    public String getStringBreed() {
        return breed_value;
    }

    @Override
    public int getColorFill() {
        return getNumber() != 0 ? color_value : Color.TRANSPARENT;
    }

    @Override
    public String getStringNumber() {
        return getNumber() != 0 ? "Корова №"+String.valueOf(cow.getNumber()) : "Нет";
    }

    @Override
    public int getNumber() {
        return cow.getNumber();
    }

    @Override
    public int getBreed() {
        return cow.getBreed();
    }

    @Override
    public int getColor() {
        return cow.getColor();
    }

    @Override
    public Date getBirthday() {
        return cow.getBirthday();
    }

    public void setBirthday(Date birthday) {
        cow.setBirthday(birthday);
    }

    @Override
    public int getMother() {
        return cow.getMother();
    }

    @Override
    public int getFather() {
        return cow.getFather();
    }
}