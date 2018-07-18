package ru.weblokos.mtckorovi.DB.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.Date;

import ru.weblokos.mtckorovi.BR;
import ru.weblokos.mtckorovi.DB.Converter.DateConverter;
import ru.weblokos.mtckorovi.Model.CowData;

@Entity(tableName = "cow_data")
public class CowDataEntity implements CowData { // замеры с коровы (вес, надой, жирность)

    @Ignore
    public static final int COW_DATA_TYPE_MASS = 1;
    @Ignore
    public static final int COW_DATA_TYPE_MILK_COUNT = 2;
    @Ignore
    public static final int COW_DATA_TYPE_FAT_CONTENT = 3;

    @Ignore
    public String[] name_types = new String[]{
            "Вес",
            "Надой",
            "Жирность молока"
    };
    @Ignore
    public String[] name_symbols = new String[]{
            "кг.",
            "л.",
            "%"
    };

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cow_id")
    private int cowId; // корова

    private int type; // тип замера (1 - вес кг, 2 - надой литров, 3 - жирность %)

    private Date date; // дата замера


    private double data; // сам замер

    public CowDataEntity() {

    }

    public CowDataEntity(int cowId) {
        this.cowId = cowId;
        this.type = COW_DATA_TYPE_MASS;
        this.date = new Date();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getCowId() {
        return cowId;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public Date getDate() {
        return date;
    }


    @Override
    public double getData() {
        return data;
    }

    @Override
    public String getTypeString() {
        switch (getType()) {
            case COW_DATA_TYPE_MASS:
                return name_types[0];
            case COW_DATA_TYPE_MILK_COUNT:
                return name_types[1];
            case COW_DATA_TYPE_FAT_CONTENT:
                return name_types[2];
            default:
                return "unknown";
        }
    }

    @Override
    public String getDateString() {
        return DateConverter.dateToString(getDate());
    }

    @Override
    public String getDataString() {
        String symbol;
        switch (getType()) {
            case COW_DATA_TYPE_MASS:
                symbol = name_symbols[0];
                break;
            case COW_DATA_TYPE_MILK_COUNT:
                symbol = name_symbols[1];
                break;
            case COW_DATA_TYPE_FAT_CONTENT:
                symbol = name_symbols[2];
                break;
            default:
                symbol = "unknown";
                break;
        }
        return String.format("%,.2f %s", getData(), symbol);
    }

    public String getDataValueString() {
        return String.format("%.2f", getData());
    }

    public void setCowId(int cowId) {
        this.cowId = cowId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;

    }

    public void setData(double data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }
}