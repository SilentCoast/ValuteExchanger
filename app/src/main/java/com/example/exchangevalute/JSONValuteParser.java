package com.example.exchangevalute;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONValuteParser {
    ArrayList<Valute> valutes;

    public ArrayList<Valute> getValutes(){ return valutes;}
    String filePath;
    Valute currentValute;

    public JSONValuteParser(String filePath){
        this.filePath = filePath;
        valutes = new ArrayList<>();
    }


    public boolean parse(){

        try {
            FileReader reader = new FileReader(filePath);

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(reader);

            JsonArray valutesJSON = (JsonArray) jsonObject.get("Valute");

            Iterator i = valutesJSON.iterator();
            while(i.hasNext()){
                currentValute = new Valute();
                JsonObject innerObj = (JsonObject) i.next();
                Log.d("JS", "parse: ");
                currentValute.setName((innerObj.get("Name")).toString());
                valutes.add(currentValute);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return  true;
    }

}
