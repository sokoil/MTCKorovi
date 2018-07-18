package ru.weblokos.mtckorovi.Model;

import java.util.Date;

public interface Cow { // корова

    int getId();
    String getStringBreed();
    String getStringNumber();
    int getNumber();
    int getBreed();
    int getColor();
    Date getBirthday();
    int getMother();
    int getFather();
    int getColorFill();

}
