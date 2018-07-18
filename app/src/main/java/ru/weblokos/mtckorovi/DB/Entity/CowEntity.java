package ru.weblokos.mtckorovi.DB.Entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.Model.Color;
import ru.weblokos.mtckorovi.Model.Cow;

import static android.arch.persistence.room.ForeignKey.CASCADE;
import static android.arch.persistence.room.ForeignKey.SET_DEFAULT;

@Entity(tableName = "cows")
public class CowEntity implements Cow { // Корова


    @PrimaryKey(autoGenerate = true)
    private int id;
    private int number; // номер на бирке
    private int breed; // порода \ масть
    private int color;
    private Date birthday; // дата рождения
    private int mother;
    private int father;
    @Ignore
    String stringBreed;

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMother(int mother) {
        this.mother = mother;
    }

    public void setFather(int father) {
        this.father = father;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getStringBreed() {
        return stringBreed;
    }

    public void setStringBreed(String stringBreed) {
        this.stringBreed = stringBreed;
    }

    public CowEntity(int number, int breed, int color, Date birthday) {
        this.number = number;
        this.breed = breed;
        this.color = color;
        this.birthday = birthday;
    }

    @Override
    public int getColorFill() {
        return color;
    }

    @Override
    public String getStringNumber() {
        return String.valueOf(number);
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getBreed() {
        return breed;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public int getMother() {
        return mother;
    }

    @Override
    public int getFather() {
        return father;
    }
}
