package in.delbird.chemist.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.delbird.chemist.R;

public class TermsAndConditionActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView mTitle;
    ImageView chat_IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        init();



    }
    public void init(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        chat_IV = (ImageView)findViewById(R.id.image_chat);
        chat_IV.setVisibility(View.INVISIBLE);
        mTitle.setText("Terms And Conditions");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
