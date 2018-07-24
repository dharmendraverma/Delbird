package in.delbird.chemist.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import in.delbird.chemist.R;


/**
 * Created by Dharmendra on 30/1/16.
 */
public class AboutUs extends AppCompatActivity {
    WebView aboutus;
    Toolbar mToolbar;
    private TextView mTitle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("About Us");
        mToolbar.setTitle("About Us");
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openUrl() {

        aboutus.loadUrl("http://delbird.in");
        aboutus.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AboutUs.this, StartPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
