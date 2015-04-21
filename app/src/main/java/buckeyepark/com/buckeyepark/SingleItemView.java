package buckeyepark.com.buckeyepark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleItemView extends Activity {
    private WebView mWebView;
    private final String tag = "tagged point";
    int clickCounter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String garageName = b.getString("rank");
        String url = getGarageURL(garageName);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mWebView = new WebView(this);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView  view, String  url){
                return true;
            }

            @Override
            public void onLoadResource(WebView  view, String  url) {

            }
        });

        this.setContentView(mWebView);
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public String getGarageURL(String garageName) {
        switch (garageName) {
            case "West Lane Avenue":
                //   "West Lane Avenue";
                return "http://osu.campusparc.com/osu/garages/west-lane";
            case "Arps Hall":
                //  "Arps Hall";
                return "http://osu.campusparc.com/osu/garages/arps" ;
            case "Tuttle Park Place":
                //   "Tuttle Park Place";
                return "http://osu.campusparc.com/osu/garages/tuttle-park-place" ;
            case "9th Avenue East":
                //   "9th Avenue East";
                return "http://osu.campusparc.com/osu/garages/9th-avenue-east";
            case "Neil Avenue":
                //  "Neil Avenue";
                return "http://osu.campusparc.com/osu/garages/neil-avenue";
            case "11th Avenue":
                //  "11th Avenue";
                return "http://osu.campusparc.com/osu/garages/11th-avenue";
            case "South Gateway":
                //  "South Gateway";
                return "http://osu.campusparc.com/osu/garages/south-campus-gateway";
            case "Lane Avenue":
                //  "Lane Avenue";
                return "http://osu.campusparc.com/osu/garages/lane-avenue";
            case "Ohio Union North":
                //  "Ohio Union North";
                return "http://osu.campusparc.com/osu/garages/ohio-union-north";
            case "North Cannon":
                // "North Cannon";
                return "http://osu.campusparc.com/osu/garages/north-cannon";
            case "Ohio Union South":
                //  "Ohio Union South";
                return "http://osu.campusparc.com/osu/garages/ohio-union-south";
            case "12th Avenue":
                //"12th Avenue";
                return "http://osu.campusparc.com/osu/garages/12th-avenue";
            case "SafeAuto Hospital":
                // "SafeAuto Hospital";
                return "http://osu.campusparc.com/osu/garages/safeauto";
            case "South Cannon":
                // "South Cannon";
                return "http://osu.campusparc.com/osu/garages/south-cannon";
            case "Northwest":
                //  "Northwest";
                return "http://osu.campusparc.com/osu/garages/northwest";
            case "9th Avenue West":
                // "9th Avenue West";
                return "http://osu.campusparc.com/osu/garages/9th-avenue-west";

        }
        return "";
    }
}