package com.golemtron.currencycalculator;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText editTextAmountOne;
    EditText editTextAmountTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCurrencyValues();
        printCurrencyValuesFromDB();

        editTextAmountOne = (EditText)findViewById(R.id.editTextAmountOne);
        editTextAmountTwo = (EditText)findViewById(R.id.editTextAmountTwo);




        editTextAmountOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextAmountTwo.setText(editTextAmountOne.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void printCurrencyValuesFromDB() {
        DatabaseHandler db = new DatabaseHandler(this);

        Log.d("Count === ", db.getCountryCount()+ "");

        for (Country c:db.getAllCountries()) {
            Log.d("Name : ",""+c.getName());
            Log.d("Price : ",""+c.getDollarEquivalent());
            Log.d("Symbol : ",""+c.getCurrencyPostfix());
        }
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

    }
    public void imageBtnCountryTwo(View view){

    }
    public void message(String mes){
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();

    }

}
