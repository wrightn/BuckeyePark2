package buckeyepark.com.buckeyepark;
import buckeyepark.com.buckeyepark.ListViewAdapter;

        import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
        import java.util.HashMap;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Intent;
import android.os.AsyncTask;
        import android.os.Bundle;
        import android.app.Activity;
        import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends Activity {
    private final String tag = "tagged point";
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    ArrayList<String> nameList;
    ArrayList<String> percentList;
    static String GARAGENAME = "Garage Name";
    static String PERCENTAGE = "Percent Full";
    static String KEYCARDACCESS = "KeyCard Access";
    static String GARAGEPIC = "Garage Pic";
    static final String WESTLANE ="http://osu.campusparc.com/images/garage-photos/west-lane-garage.jpg?sfvrsn=2";
    static final String OHIOUNIONSOUTH = "http://osu.campusparc.com/images/garage-photos/ohio-southgarage.jpg?sfvrsn=0";
    static final String ARPSHALL = "http://osu.campusparc.com/images/garage-photos/arps-garage.jpg?sfvrsn=0";
    static final String TWELVEAVE = "https://www.osu.edu/map/buildingImg.php?id=387&size=campusmap";
    static final String SOUTHGATEWAY ="http://osu.campusparc.com/images/page-header/web-pageheader-gateway.jpg?sfvrsn=0";
    static final String TUTTLEPARKPLACE ="http://osu.campusparc.com/images/page-header/web-pageheader-tuttle.jpg?sfvrsn=0";
    static final String OHIOUNIONNORTH ="http://osu.campusparc.com/images/garage-photos/north-garage.jpg?sfvrsn=0";
    static final String NINTHAVENUEEAST ="http://osu.campusparc.com/images/page-header/web-pageheader-9theast.jpg?sfvrsn=0";
    static final String LANEAVE ="http://osu.campusparc.com/images/misc-images/lag7.jpg?sfvrsn=0";
    static final String NORTHCANNON ="http://osu.campusparc.com/images/garage-photos/north-cannon-garage.jpg?sfvrsn=0";
    static final String ELEVENTHAVE ="http://osu.campusparc.com/images/garage-photos/11thavegarage.jpg?sfvrsn=4";
    static final String NEILAVE ="http://osu.campusparc.com/images/garage-photos/neil-avegarage.jpg?sfvrsn=0";
    static final String SOUTHCANNON ="http://osu.campusparc.com/images/garage-photos/south-cannon-garage.jpg?sfvrsn=0";
    static final String SAFEAUTO ="http://osu.campusparc.com/images/page-header/web-pageheader-safeauto.jpg?sfvrsn=0";
    static final String NINTHWEST ="http://osu.campusparc.com/images/page-header/web-pageheader-9thwest.jpg?sfvrsn=0";
    static final String NORTHWEST ="http://osu.campusparc.com/images/garage-photos/northwestgarage.jpg?sfvrsn=0";


    // URL Address
    String url = "http://osu.campusparc.com/Sitefinity/Public/Services/GarageGraph.asmx/load?GarageId=0&GradientBarWidth=190";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        Button clickButton = (Button) findViewById(R.id.graphButton);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d(tag,"I see you clicking");
                Intent intent = new Intent(MainActivity.this, HorizontalBarChartActivity.class);
                intent.putExtra("name", nameList);
                intent.putExtra("percent", percentList);
                startActivity(intent);
            }
        });
        // Execute DownloadJSON AsyncTask
        new JsoupListView().execute();

    }

    // Title AsyncTask
    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Gathering Garage data");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            nameList = new ArrayList<String>();
            percentList = new ArrayList<String>();

            try {
                String url = "http://osu.campusparc.com/Sitefinity/Public/Services/GarageGraph.asmx/load?GarageId=0&GradientBarWidth=190";

                String html1 = "";
                String html2 = "";
                Document doc = Jsoup.connect(url).get();
                String html = doc.toString();
                html1 = html.replaceAll("&lt;", "<");
                html2 = html1.replaceAll("&gt;", ">");

                Document doc_new = Jsoup.parse(html2);
                for (Element table: doc_new.select("TABLE[class = graphDataTable multiple]")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element row = table.select("tr[class=graphDataRow]").first();
                    Element garagePercentage = row.select("td").get(2);
//                String result = td.text();
                    Element linkToGarage = row.select("td").get(0);
                    String garageLink = linkToGarage.attr("href");
                    String garageName = linkToGarage.text();
                    Element visitorAccess = row.select("td").get(3);
                    Element KeyCardAccess = row.select("td").get(4);
                    String imgSrcStr = getImageUrl(garageName);
                    Log.d("name", garageName);
                    Log.d("percentage", garagePercentage.text());
                    Log.d("pic", imgSrcStr);

                    // Retrive Jsoup Elements
                    // Get the first td
                    map.put("Garage Name", garageName);
                    // Get the second td
                    map.put("Percent Full", garagePercentage.text());
                    // Get the third td
                   // map.put("", tds.get(2).text());
                    // Get the image src links
                    map.put("Garage Pic", imgSrcStr);
                    // Set all extracted Jsoup Elements into the array
                    nameList.add(garageName);
                    percentList.add(garagePercentage.text());
                    arraylist.add(map);
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        private String getImageUrl(String garageName){
            switch (garageName) {
                case "West Lane Avenue":
                    //   "West Lane Avenue";
                    return MainActivity.WESTLANE;
                case "Arps Hall":
                    //  "Arps Hall";
                    return  MainActivity.ARPSHALL;
                case "Tuttle Park Place":
                    //   "Tuttle Park Place";
                    return MainActivity.TUTTLEPARKPLACE ;
                case "9th Avenue East":
                    //   "9th Avenue East";
                    return MainActivity.NINTHAVENUEEAST;
                case "Neil Avenue":
                    //  "Neil Avenue";
                    return MainActivity.NEILAVE;
                case "11th Avenue":
                    //  "11th Avenue";
                    return MainActivity.ELEVENTHAVE;
                case "South Gateway":
                    //  "South Gateway";
                    return MainActivity.SOUTHGATEWAY;
                case "Lane Avenue":
                    //  "Lane Avenue";
                    return MainActivity.LANEAVE;
                case "Ohio Union North":
                    //  "Ohio Union North";
                    return MainActivity.OHIOUNIONNORTH;
                case "North Cannon":
                    // "North Cannon";
                    return MainActivity.NORTHCANNON;
                case "Ohio Union South":
                    //  "Ohio Union South";
                    return MainActivity.OHIOUNIONSOUTH;
                case "12th Avenue":
                    //"12th Avenue";
                    return MainActivity.TWELVEAVE;
                case "SafeAuto Hospital":
                    // "SafeAuto Hospital";
                    return MainActivity.SAFEAUTO;
                case "South Cannon":
                    // "South Cannon";
                    return MainActivity.SOUTHCANNON;
                case "Northwest":
                    //  "Northwest";
                    return MainActivity.NINTHWEST;
                case "9th Avenue West":
                    // "9th Avenue West";
                    return MainActivity.NORTHWEST;

            }
            return "";
        }

            @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }




}