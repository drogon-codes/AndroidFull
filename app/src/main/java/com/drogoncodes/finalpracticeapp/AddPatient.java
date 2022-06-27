package com.drogoncodes.finalpracticeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddPatient extends AppCompatActivity {
    String[] illness ={"Maleria","Jaundice","Typhoid","Covid19","Cold","Viral"};
    RadioButton radioGender;
    RadioGroup radioGroup;
    Integer patientId=0;
    EditText patientName, patientContact;
    Button addPatient;
    DBHelper db = new DBHelper(this, null, null,1);
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        //Autocomplete Textview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,illness);
        AutoCompleteTextView actv = (AutoCompleteTextView)findViewById(R.id.etPatientIllness);
        actv.setThreshold(1);
        actv.setAdapter(adapter);

        //RadioButton
        radioGroup = (RadioGroup) findViewById(R.id.rgGender);
        int selectId = radioGroup.getCheckedRadioButtonId();

        patientName = findViewById(R.id.etPatientName);
        patientContact = findViewById(R.id.etPatientContact);
        addPatient = findViewById(R.id.btnAddPatient);
        Bundle extras = getIntent().getExtras();

        addPatient.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if(patientId == 0){
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioGender = (RadioButton) findViewById(selectedId);
                    db.AddPatient(patientName.getText().toString(), actv.getText().toString(), patientContact.getText().toString(), radioGender.getText().toString());
                    Intent i = new Intent(AddPatient.this, ViewPatient.class);
                    startActivity(i);
                }
                else{
                    //delete ni method
                    //update ni method
                }
            }
        });

        if(extras != null){
            String name = extras.getString("name");
            Cursor c = db.GetPatient(name);
            while(c.moveToNext()){
                patientId = Integer.parseInt(c.getString(c.getColumnIndex("patientId")).toString());
                patientName.setText(c.getString(c.getColumnIndex("patientName")).toString());
                actv.setText(c.getString(c.getColumnIndex("patientIllness")).toString());
                patientContact.setText(c.getString(c.getColumnIndex("patientContact")).toString());
                if(c.getString(c.getColumnIndex("patientGender")).toString().contains("Male")){
                    RadioButton rbMale = findViewById(R.id.rbGenderMale);
                    rbMale.setChecked(true);
                }
                else
                {
                    RadioButton rbFemale = findViewById(R.id.rbGenderFemale);
                    rbFemale.setChecked(true);
                }
            }
        }

    }
}