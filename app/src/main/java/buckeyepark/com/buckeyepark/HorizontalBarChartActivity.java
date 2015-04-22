package buckeyepark.com.buckeyepark;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.util.ArrayList;
import java.util.HashMap;

public class HorizontalBarChartActivity extends Activity implements OnChartValueSelectedListener {
    ArrayList<String> garageNames;
    ArrayList<String> garagePercents;
    protected HorizontalBarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        garageNames = b.getStringArrayList("name");
        garagePercents = b.getStringArrayList("percent");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_horizontalbarchart);

        mChart = (HorizontalBarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMaxValue((float) 100.00);

        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);

        setData(12, 50);
        mChart.animateY(500);

    }


    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        int counter = 0;
        for (String name : garageNames) {
            String[] separated = garagePercents.get(counter).split("%");
            float val = Float.valueOf(separated[0]);
            xVals.add(String.valueOf(name));
            yVals1.add(new BarEntry(val, counter));
            counter++;
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mChart.setData(data);
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        if (entry == null)
            return;

        String garageTemp = entry.toString();
        String positionTemp[] = garageTemp.split(" ");
        String position = positionTemp[2];
        Log.i("position", position);

        String garageCurName = garageNames.get(Integer.valueOf(position));
        Log.i("garageCurName ", garageCurName);

        Uri geoUri = Uri.parse(getGarageAddress(garageCurName));
        Intent mapCall = new Intent(Intent.ACTION_VIEW, geoUri);
        startActivity(mapCall);
    }

    public void onNothingSelected() {
    };

    public String getGarageAddress(String garageName) {
        switch (garageName) {
            case "West Lane Avenue":
                //   "West Lane Avenue";
                return "geo:0,0?q=328 W. Lane Ave Columbus, Ohio";
            case "Arps Hall":
                //  "Arps Hall";
                return "geo:0,0?q=1990 College Road Columbus, Ohio" ;
            case "Tuttle Park Place":
                //   "Tuttle Park Place";
                return "geo:0,0?q=2050 Tuttle Park Place Columbus, Ohio" ;
            case "9th Avenue East":
                //   "9th Avenue East";
                return "geo:0,0?q=345 West 9th Avenue Columbus, Ohio";
            case "Neil Avenue":
                //  "Neil Avenue";
                return "geo:0,0?q=1801 Neil Avenue Columbus, Ohio";
            case "11th Avenue":
                //  "11th Avenue";
                return "geo:0,0?q=229 West 11th Avenue Columbus, Ohio";
            case "South Gateway":
                //  "South Gateway";
                return "geo:0,0?q=75 East 11th Avenue Columbus, Ohio";
            case "Lane Avenue":
                //  "Lane Avenue";
                return "geo:0,0?q=2105 Neil Avenue Columbus, Ohio";
            case "Ohio Union North":
                //  "Ohio Union North";
                return "geo:0,0?q=1780 College Road Columbus, Ohio";
            case "North Cannon":
                // "North Cannon";
                return "geo:0,0?q=1640 Cannon Drive Ave Columbus, Ohio";
            case "Ohio Union South":
                //  "Ohio Union South";
                return "geo:0,0?q=1759 North High Street Columbus, Ohio";
            case "12th Avenue":
                //"12th Avenue";
                return "geo:0,0?q=340 West 12th Avenue Columbus, Ohio";
            case "SafeAuto Hospital":
                // "SafeAuto Hospital";
                return "geo:0,0?q=1585 Westpark Street Columbus, Ohio";
            case "South Cannon":
                // "South Cannon";
                return "geo:0,0?q=1640 Cannon Drive Columbus, Ohio";
            case "Northwest":
                //  "Northwest";
                return "geo:0,0?q=271 Ives Drive  Columbus, Ohio";
            case "9th Avenue West":
                // "9th Avenue West";
                return "geo:0,0?q=355 West 9th Avenue Columbus, Ohio";

        }
        return "";
    }
}