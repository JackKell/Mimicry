package com.github.jackkell.mimicryproject.mainactivities;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jackkell.mimicryproject.DatabaseOpenHelper;
import com.github.jackkell.mimicryproject.DatabaseStorable;
import com.github.jackkell.mimicryproject.Impersonator;
import com.github.jackkell.mimicryproject.R;
import com.github.jackkell.mimicryproject.TwitterUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpersonatorCreationActivity extends Activity {

    EditText etImpersonatorName;
    EditText et1;
    EditText et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impersonator_creation);
        etImpersonatorName = (EditText) findViewById(R.id.impersonatorNameEditText);
        et1 = (EditText) findViewById(R.id.EditText1);
        et2  = (EditText) findViewById(R.id.EditText2);
        Button createImpersonatorButton = (Button) findViewById(R.id.createButton);

        createImpersonatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateImpersonatorButtonClick();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_impersonator_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onCreateImpersonatorButtonClick(){
        if (etImpersonatorName.getText().length() != 0 && et1.getText().length() != 0 && et2.getText().length() != 0){
            Toast.makeText(this, "YAYAY!!", Toast.LENGTH_LONG).show();
            Impersonator impersonator = createImpersonator();
            SQLiteDatabase db = DatabaseOpenHelper.getDatabase(this);
            impersonator.addToDatabase(db);
        } else {
            Toast.makeText(this, "Please fill in required fields.", Toast.LENGTH_LONG).show();
        }
    }

    private Impersonator createImpersonator(){
        List<TwitterUser> twitterUserList = new ArrayList<>(2);
        twitterUserList.add(new TwitterUser(et1.getText().toString()));
        twitterUserList.add(new TwitterUser(et2.getText().toString()));
        return new Impersonator(etImpersonatorName.getText().toString(), twitterUserList, new Date());
    }
}
