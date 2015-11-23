package com.golemtron.currencycalculator;

/**
 * Created by Taha Rushain on 11/23/15.
 */
public class Country {
    private int id;
    private String name;
    private String currencyPostfix;
    private float dollarEquivalent; // Currency rate equivalent to One Dollar

    public Country(){

    }
    public Country(int id, String name, String postfix, float dollarEquivalent){
        this.id = id;
        this.name = name;
        this.currencyPostfix = postfix;
        this.dollarEquivalent = dollarEquivalent;
    }

    public float getDollarEquivalent() {
        return dollarEquivalent;
    }

    public void setDollarEquivalent(float dollarEquivalent) {
        this.dollarEquivalent = dollarEquivalent;
    }

    public Country(String name, String postfix, float dollarEquivalent){
        this.name = name;
        this.currencyPostfix = postfix;
        this.dollarEquivalent = dollarEquivalent;

    }

    public String getName() {
        return name;
    }

    public String getCurrencyPostfix() {
        return currencyPostfix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrencyPostfix(String currencyPostfix) {
        this.currencyPostfix = currencyPostfix;
    }

    public int getId(){
        return id;
    }
}
