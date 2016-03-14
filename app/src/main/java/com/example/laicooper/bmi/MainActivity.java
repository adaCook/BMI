package com.example.laicooper.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    EditText vWeight;
    String[] feetArray, inchesArray;
    int feet, inches;
    private ImageView ivContext;
    Spinner spinnerFeet, spinnerInches;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivContext = (ImageView) findViewById(R.id.image);
        registerForContextMenu(ivContext);
        vWeight = (EditText) findViewById(R.id.weight);
        feetArray = getResources().getStringArray(R.array.feet);
        inchesArray = getResources().getStringArray(R.array.inches);
        spinnerFeet = (Spinner) findViewById(R.id.spinner_feet);
        ArrayAdapter<String> adapterFeet = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, feetArray);
        spinnerFeet.setAdapter(adapterFeet);
        spinnerInches = (Spinner) findViewById(R.id.spinner_inches);
        ArrayAdapter<String> adapterInches = new ArrayAdapter<String>(this,
                R.layout.dropdown_item, inchesArray);
        spinnerInches.setAdapter(adapterInches);
        spinnerFeet.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                feet = arg0.getSelectedItemPosition() + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerInches.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                inches = arg0.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences();
    }

    public void calcBMI(View view) {
        String weight = vWeight.getText().toString();
        savePreferences(feet - 1, inches, weight);
        Intent intent = new Intent(this, ReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("feet", feet);
        bundle.putInt("inches", inches);
        bundle.putString("weight", weight);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void savePreferences(int f, int i, String w) {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        pref.edit().putInt("feet", f).commit();
        pref.edit().putInt("inches", i).commit();
        pref.edit().putString("weight", w).commit();
    }

    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        spinnerFeet.setSelection(pref.getInt("feet", 0));
        spinnerInches.setSelection(pref.getInt("inches", 0));
        vWeight.setText(pref.getString("weight", "0"));
    }

    public void aboutBMI(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.about_button)
                .setMessage(R.string.about_msg)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {

                            }
                        })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                openOptionsDialog();
                return true;
            case R.id.menu_wiki:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
            case R.id.menu_exit:
                finish();
                return true;

        }

        return false;
    }

    public void openOptionsDialog() {
        new AlertDialog.Builder(MainActivity.this)

                .setTitle(R.string.menu_about)

                .setMessage(R.string.about_msg)

                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                            }
                        }).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAboutBMI:
                new AlertDialog.Builder(MainActivity.this)

                        .setTitle(R.string.menu_about)

                        .setMessage(R.string.about_msg)

                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialoginterface, int i) {
                                    }
                                }).show();
                return true;
            case R.id.menuBMIWiki:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}

