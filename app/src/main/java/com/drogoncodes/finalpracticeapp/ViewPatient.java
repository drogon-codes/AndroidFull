package com.drogoncodes.finalpracticeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewPatient extends AppCompatActivity {

    ListView lv;
    ArrayList<String> _list = new ArrayList<String>();
    ArrayAdapter<String> adp;
    DBHelper db = new DBHelper(this, null, null, 1);
    @SuppressLint({"Range", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        setTitle("All Patients");
        lv = findViewById(R.id.lv1);
        Cursor patientList = db.ViewPatients();
//        db.AddPatient("Somesh", "Viral", "9663022104", "Male");
        if(patientList!=null){
            patientList.moveToFirst();
            do{
                _list.add(patientList.getString(patientList.getColumnIndex("patientName")).toString());
//                _list.add(patientList.getString(patientList.getColumnIndex("patientIllness")).toString());
//                _list.add(patientList.getString(patientList.getColumnIndex("patientContact")).toString());
//                _list.add(patientList.getString(patientList.getColumnIndex("patientGender")).toString());
            }
            while(patientList.moveToNext());

            adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, _list);
            lv.setAdapter(adp);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i2 = new Intent(ViewPatient.this, AddPatient.class);
                i2.putExtra("name", _list.get(i));
                startActivity(i2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addPatient:
                Intent i = new Intent(ViewPatient.this, AddPatient.class);
                startActivity(i);
                return true;
            case R.id.viewPatients:
                Intent i2 = new Intent(ViewPatient.this, ViewPatient.class);
                startActivity(i2);
                return true;
            case R.id.logout:
                SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLogin", false);
                editor.apply();
                Toast.makeText(getApplicationContext(),"User Logged Out!",Toast.LENGTH_LONG).show();
                Intent i3 = new Intent(ViewPatient.this, MainActivity.class);
                startActivity(i3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}