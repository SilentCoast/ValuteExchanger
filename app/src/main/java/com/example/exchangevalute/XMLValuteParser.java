package com.example.exchangevalute;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.List;

public class XMLValuteParser {
    ArrayList<Valute> valutes;

    public XMLValuteParser(){
        valutes = new ArrayList<>();
    }

    public ArrayList<Valute> getValutes(){
        return valutes;
    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        Valute currentValute = null;
        boolean inEntry = false;
        String textValue="";

        try {
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("Valute".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentValute = new Valute();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("Valute".equalsIgnoreCase(tagName)){
                                valutes.add(currentValute);
                                inEntry = false;
                            }
                            else if("Name".equalsIgnoreCase(tagName)){
                                currentValute.setName(textValue);
                            }
                            else if ("Value".equalsIgnoreCase(tagName)){
                                textValue = textValue.replaceAll(",",".");
                                currentValute.setValue(Double.valueOf(textValue));
                            }
                            else if ("CharCode".equalsIgnoreCase(tagName)){
                                currentValute.setCharCode(textValue);
                            }
                            else if ("Nominal".equalsIgnoreCase(tagName)){
                                currentValute.setNominal(Integer.valueOf( textValue));
                            }
                        }
                        break;
                    default:

                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

}
