package com.katerinachinnappan.homework2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //this puts the text from EditText field to sharedString2 in AppInfo
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        if (appInfo.sharedString2 != null) {
            edv2.setText(appInfo.sharedString2);
        }
        //set the TextView for String1 and take the value from sharedString1 which is in AppInfo
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText(appInfo.sharedString1);

        //set the TextView for String3 and take the value from sharedString3 which is in AppInfo
        TextView tv3 = (TextView) findViewById(R.id.textView8);
        tv3.setText(appInfo.sharedString3);

    }
    //switch to Activity1
    public void onClickGo1(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
    //switch to Activity3
    public void onClickGo2(View v){
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
    //when "Enter" is clicked, grab the string from the field and pass it to setString2 in AppInfo
    public void onClickEnter1(View v){
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setString2(text2);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //go back to Activity1, set flag
    public void clickBack(View V) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

}

