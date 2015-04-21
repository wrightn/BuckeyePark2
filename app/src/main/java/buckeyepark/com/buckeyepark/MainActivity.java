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

import android.os.AsyncTask;
        import android.os.Bundle;
        import android.app.Activity;
        import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ListView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends Activity {
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    static String GARAGENAME = "Garage Name";
    static String PERCENTAGE = "Percent Full";
    static String KEYCARDACCESS = "KeyCard Access";
    static String VISITORACCESS = "Visitor Access";
    // URL Address
    String url = "http://osu.campusparc.com/Sitefinity/Public/Services/GarageGraph.asmx/load?GarageId=0&GradientBarWidth=190";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
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
            mProgressDialog.setTitle("Android Jsoup ListView Tutorial");
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

            try {
                String url = "http://osu.campusparc.com/Sitefinity/Public/Services/GarageGraph.asmx/load?GarageId=0&GradientBarWidth=190";

                String html1 = "";
                String html2 = "";
                Document doc = Jsoup.connect(url).get();
                String html = doc.toString();
                html1 = html.replaceAll("&lt;", "<");
                html2 = html1.replaceAll("&gt;", ">");

                Document doc_new = Jsoup.parse(html2);
               // Elements table = doc_new.select("TABLE[class = graphDataTable multiple]");
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
                    String imgSrcStr = visitorAccess.attr("src");
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
                  //  map.put("Visitor Access", imgSrcStr);
                    // Set all extracted Jsoup Elements into the array
                    arraylist.add(map);
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
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