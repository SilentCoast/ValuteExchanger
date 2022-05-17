package com.example.exchangevalute;

public class Valute {
    int NumCode;
    String CharCode;
    int Nominal;
    String Name;
    Double Value;

    public Double getValue() {
        return Value;
    }

    public int getNominal() {
        return Nominal;
    }

    public int getNumCode() {
        return NumCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public String getName() {
        return Name;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNominal(int nominal) {
        Nominal = nominal;
    }

    public void setNumCode(int numCode) {
        NumCode = numCode;
    }

    public void setValue(Double value) {
        Value = value;
    }
}
