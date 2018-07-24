package ru.weblokos.mtckorovi.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CartesianSeriesLine;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.Mapping;
import com.anychart.anychart.MarkerType;
import com.anychart.anychart.Set;
import com.anychart.anychart.Stroke;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

import ru.weblokos.mtckorovi.App;
import ru.weblokos.mtckorovi.DB.Entity.CowDataEntity;
import ru.weblokos.mtckorovi.DataRepository;
import ru.weblokos.mtckorovi.R;

import static ru.weblokos.mtckorovi.DB.Entity.CowDataEntity.COW_DATA_TYPE_FAT_CONTENT;
import static ru.weblokos.mtckorovi.DB.Entity.CowDataEntity.COW_DATA_TYPE_MASS;
import static ru.weblokos.mtckorovi.DB.Entity.CowDataEntity.COW_DATA_TYPE_MILK_COUNT;

public class CowDataChartActivity extends AppCompatActivity {

    private com.anychart.anychart.AnyChartView chartView;
    private MediatorLiveData<List<CowDataEntity>> mObservableCowData;
    private DataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow_data_chart);
        chartView = findViewById(R.id.any_chart_view);
        repository = ((App)getApplication()).getRepository();
        loadData();
    }

    private void loadData() {
        mObservableCowData = new MediatorLiveData<>();
        mObservableCowData.setValue(null);
        LiveData<List<CowDataEntity>> datas = repository.loadCowData(getIntent().getExtras().getInt("cow"));
        mObservableCowData.addSource(datas, mObservableCowData::setValue);
        mObservableCowData.observe(this,
                cowDataEntityList -> {
                    if(cowDataEntityList != null)
                        setUpChart(cowDataEntityList);
                });
    }

    private void setUpChart(List<CowDataEntity> cowDataEntityList) {
        Cartesian cartesian = AnyChart.line();
        cartesian.getCrosshair().setEnabled(true);
        cartesian.getCrosshair()
                .setYLabel(true)
                .setYStroke((Stroke) null, null, null, null, null);
        cartesian.getTooltip().setPositionMode(TooltipPositionMode.POINT);
        cartesian.setTitle("");
        cartesian.getYAxis().setTitle("");
        cartesian.getXAxis().getLabels().setPadding(5d, 5d, 5d, 5d);
        List<DataEntry> seriesData = new ArrayList<>();
        for(CowDataEntity cowData : cowDataEntityList) {
            switch (cowData.getType()) {
                case COW_DATA_TYPE_MASS:
                    seriesData.add(new CustomDataEntry(cowData.getDateString(), cowData.getData(), null, null));
                    break;
                case COW_DATA_TYPE_MILK_COUNT:
                    seriesData.add(new CustomDataEntry(cowData.getDateString(), null, cowData.getData(), null));
                    break;
                case COW_DATA_TYPE_FAT_CONTENT:
                    seriesData.add(new CustomDataEntry(cowData.getDateString(), null, null, cowData.getData()));
                    break;
                default:
                    break;
            }
        }
        Set set = new Set(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        CartesianSeriesLine series1 = cartesian.line(series1Mapping);
        series1.setName("Вес");
        series1.getHovered().getMarkers().setEnabled(true);
        series1.getHovered().getMarkers()
                .setType(MarkerType.CIRCLE)
                .setSize(4d);
        series1.getTooltip()
                .setPosition("right")
                .setAnchor(EnumsAnchor.LEFT_CENTER)
                .setOffsetX(5d)
                .setOffsetY(5d);

        CartesianSeriesLine series2 = cartesian.line(series2Mapping);
        series2.setName("Надой");
        series2.getHovered().getMarkers().setEnabled(true);
        series2.getHovered().getMarkers()
                .setType(MarkerType.CIRCLE)
                .setSize(4d);
        series2.getTooltip()
                .setPosition("right")
                .setAnchor(EnumsAnchor.LEFT_CENTER)
                .setOffsetX(5d)
                .setOffsetY(5d);

        CartesianSeriesLine series3 = cartesian.line(series3Mapping);
        series3.setName("Жирность");
        series3.getHovered().getMarkers().setEnabled(true);
        series3.getHovered().getMarkers()
                .setType(MarkerType.CIRCLE)
                .setSize(4d);
        series3.getTooltip()
                .setPosition("right")
                .setAnchor(EnumsAnchor.LEFT_CENTER)
                .setOffsetX(5d)
                .setOffsetY(5d);

        cartesian.getLegend().setEnabled(true);
        cartesian.getLegend().setFontSize(13d);
        cartesian.getLegend().setPadding(0d, 0d, 10d, 0d);

        chartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

}
