package com.yu.myfix;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    TextView tv;
    ClassA classA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.sample_text);
        classA = new ClassA();
    }

    public void callMethodA(View view) {
        tv.setText(classA.methorA());
    }

    public void callMethodB(View view) {
        tv.setText(classA.methorB());
    }

    public void fixMethod(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "classAfix.dex");
        FixManager dexManager = new FixManager(this);
        dexManager.load(file);
    }
}
