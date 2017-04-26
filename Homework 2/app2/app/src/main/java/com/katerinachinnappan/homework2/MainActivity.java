package com.katerinachinnappan.homework2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";
    static final public String PREF_STRING_1 = "string_1";
    static final public String PREF_STRING_2 = "string_2";

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //this puts the text from EditText field to sharedString1 in AppInfo
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        if (appInfo.sharedString1 != null) {
            edv1.setText(appInfo.sharedString1);
        }
        //set the TextView for String2 and take the value from sharedString2 which is in AppInfo
        TextView tv2 = (TextView) findViewById(R.id.textView3);
        tv2.setText(appInfo.sharedString2);

        //set the TextView for String3 and take the value from sharedString3 which is in AppInfo
        TextView tv3 = (TextView) findViewById(R.id.textView7);
        tv3.setText(appInfo.sharedString3);

    }
    //when "Enter" is clicked, grab the string from the field and pass it to setString1 in AppInfo
    public void onClickEnter1(View v){
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        String text1 = edv1.getText().toString();
        appInfo.setString1(text1);

    }
    //switch to Activity2
    public void onClickGo2(View v){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
    //switch to Activity3
    public void onClickGo3(View v){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    //go back to Activity1, set flag
    public void clickReturn(View V) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

}
