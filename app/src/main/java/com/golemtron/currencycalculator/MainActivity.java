package com.golemtron.currencycalculator;


import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements CountryListDialogFragment.CountryListDialogListener{

    private static String currency_symbol_one = "USD";
    private static String currency_symbol_two = "KRW";

    private String[] currency_list;
    private ArrayList<String> currency_list_al = new ArrayList<String>();

    DatabaseHandler db;

    private EditText editTextAmountOne;
    private EditText editTextAmountTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        setCurrencyValues();
        currency_list = getResources().getStringArray(R.array.country_array);
        for(int i=0; i < currency_list.length ;i++) {
            currency_list_al.add(currency_list[i]);
        }
        Collections.sort(currency_list_al);

        editTextAmountOne = (EditText)findViewById(R.id.editTextAmountOne);
        editTextAmountTwo = (EditText)findViewById(R.id.editTextAmountTwo);

        editTextAmountOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //editTextAmountTwo.setText(editTextAmountOne.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                String in = editTextAmountOne.getText().toString();
                if (in != "" && in != null && !in.isEmpty()) {
                    float value_A = Float.valueOf(in.trim().split(" ")[0]);
                    float currency_rate_A = db.getCurrency(currency_symbol_one);
                    float currency_rate_B = db.getCurrency(currency_symbol_two);
                    currency_rate_B = currency_rate_B / currency_rate_A;
                    float value_B = value_A * currency_rate_B;
                    editTextAmountTwo.setText(value_B + " " + currency_symbol_two);
                }
            }
        });

    }

    private void setCurrencyValues(){
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();
            InputStream stream = getResources().openRawResource(R.raw.currency_rates);
            myparser.setInput(stream, null);


            int event = myparser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myparser.getName();
                if (event == XmlPullParser.START_TAG && name.contains("resource")) {
                    Country country = new Country();
                    //Log.d("Parser"," "+name);
                    event = myparser.next();
                    name = myparser.getName();
                    while (!(event == XmlPullParser.END_TAG && name.contains("resource"))) {

                          if(event == XmlPullParser.START_TAG && name.contains("field")){
                            if(myparser.getAttributeValue(0).contains("name"))
                              country.setName(myparser.nextText().trim());
                            else if(myparser.getAttributeValue(0).contains("price"))
                              country.setDollarEquivalent(Float.valueOf(myparser.nextText().trim()));
                            else if(myparser.getAttributeValue(0).contains("symbol"))
                              country.setCurrencyPostfix(myparser.nextText().trim().replace("=X",""));
                          }

                        event = myparser.next();
                        name = myparser.getName();

                    }

                    DatabaseHandler db = new DatabaseHandler(this);

                    db.addCountry(country);

                }

                event = myparser.next();
            }
        }
        catch(XmlPullParserException xe){
            Log.e("EXCEPTION : ","XmlPullParserException");
        }
        catch(IOException ex){
            Log.e("EXCEPTION : ","IOEXCEPTION");
        }

    }


    public void imageBtnCountryOne(View view){

                DialogFragment dialog = new CountryListDialogFragment();
                Bundle args = new Bundle();
                args.putInt("type", 1);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "fragment_country_select");


    }
    public void imageBtnCountryTwo(View view){
        DialogFragment dialog = new CountryListDialogFragment();
        Bundle args = new Bundle();
        args.putInt("type", 2);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "fragment_country_select");

    }

    public void message(String mes){
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDialogClick(DialogFragment dialog, int which, int type) {

        String currency = currency_list_al.get(which);
        currency = currency.trim().split(":")[1];
        if(type == 1)
            currency_symbol_one = currency;
        else if(type == 2)
            currency_symbol_two = currency;
    }




}
