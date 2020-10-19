package com.birkagal.algocube;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Activity_Stats extends AppCompatActivity {

    AnyChartView anyChartView;
    private ArrayList<Solve> solves;
    private final SolveDB mSolveDB = SolveDB.getInstance();
    private Cartesian cartesian;

    private TextView stats_txt_best_time;
    private TextView stats_txt_avg_time;
    private Button stats_btn_clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        findViews();
        solves = mSolveDB.getSolves();
        set_labels();
        showChart();

        stats_btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked();
            }
        });
    }

    private void buttonClicked() {
        mSolveDB.clearSolves();
        solves = new ArrayList<>();
        cartesian.data(formatData());
        set_labels();
    }

    private void set_labels() {
        ArrayList<Solve> sortedList = new ArrayList<>(solves);
        Collections.sort(sortedList);
        String best_time = get_best_time();
        String average_time = get_average_time(sortedList);
        stats_txt_best_time.setText(best_time);
        stats_txt_avg_time.setText(average_time);
    }

    private String get_best_time() {
        if (solves.isEmpty())
            return "N/A";
        return solves.get(0).getFormattedTime();
    }

    private String get_average_time(ArrayList<Solve> list) {
        if (solves.isEmpty())
            return "N/A";
        Long average = calculateAverage(list);
        return mSolveDB.getFormattedTime(average);
    }

    private Long calculateAverage(@NotNull ArrayList<Solve> list) {
        Long sum = -1L;
        if (!list.isEmpty()) {
            for (Solve solve : list) {
                sum += solve.getSolveTime();
            }
            return sum / list.size();
        }
        return sum;
    }

    private void showChart() {
        cartesian = AnyChart.column();

        List<DataEntry> data = formatData();

        Column column = cartesian.column(data);
        column.labels(true);
        column.labels().format("{%Value}");

        cartesian.animation(true);
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}s");

        cartesian.title("All Solves");
        cartesian.xAxis(0).title("Solve Number");
        cartesian.yAxis(0).title("Time");

        anyChartView.setChart(cartesian);
    }

    @NotNull
    private List<DataEntry> formatData() {
        List<DataEntry> data = new ArrayList<>();
        double solve = 0;
        if (solves.size() == 0) {
            data.add(new ValueDataEntry("N/A", 0));
        } else {
            for (int i = 0; i < solves.size(); i++) {
                solve = solves.get(i).getSolveTime();
                solve /= 1000;
                solve = (double) ((int) (solve * 100)) / (100);
                data.add(new ValueDataEntry(i, solve));
            }
        }
        return data;
    }

    private void findViews() {
        anyChartView = findViewById(R.id.stats_chart);
        stats_txt_best_time = findViewById(R.id.stats_txt_best_time);
        stats_txt_avg_time = findViewById(R.id.stats_txt_avg_time);
        stats_btn_clear = findViewById(R.id.stats_btn_clear);
    }
}
