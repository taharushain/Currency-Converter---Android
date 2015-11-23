package com.golemtron.currencycalculator;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Taha Rushain on 11/23/15.
 */

public class CurrencyManager {


    private static final String api = "http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote";

    public CurrencyManager(){


    }

}
