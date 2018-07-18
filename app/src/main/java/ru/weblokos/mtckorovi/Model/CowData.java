package ru.weblokos.mtckorovi.Model;

import java.util.Date;

public interface CowData { // замеры действий с коровой

    int getId();
    int getCowId();
    int getType();
    Date getDate();
    double getData();

    String getTypeString();
    String getDateString();
    String getDataString();
}
