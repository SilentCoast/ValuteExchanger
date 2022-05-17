package com.example.exchangevalute;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
ListView listValutes;
Button btnExchange;
TextView txtFinal;
TextView txtCurrentValuteName;
TextView textViewfinalCharCode;
public ArrayList<Valute> valutes;
ProgressDialog pd;
    ValutesAdapter adapter;

    TextView txtCurrent;
    double currentValue;
    int currentNominal;
   public Valute currentValute;
    String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listValutes = findViewById(R.id.listValutes);
        btnExchange = findViewById(R.id.btnExchange);
        txtFinal = findViewById(R.id.txtFinal);
        txtCurrent = findViewById(R.id.txtCurrent);
        textViewfinalCharCode = findViewById(R.id.textViewFinalCharCode);
        txtCurrentValuteName = findViewById(R.id.txtCurrentValuteName);


        {
            content = "{\n" +
                    "  \"Date\": \"2022-05-07T11:30:00+03:00\",\n" +
                    "  \"PreviousDate\": \"2022-05-06T11:30:00+03:00\",\n" +
                    "  \"PreviousURL\": \"\\/\\/www.cbr-xml-daily.ru\\/archive\\/2022\\/05\\/06\\/daily_json.js\",\n" +
                    "  \"Timestamp\": \"2022-05-07T20:00:00+03:00\",\n" +
                    "  \"Valute\": {\n" +
                    "    \"AUD\": {\n" +
                    "      \"ID\": \"R01010\",\n" +
                    "      \"NumCode\": \"036\",\n" +
                    "      \"CharCode\": \"AUD\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Австралийский доллар\",\n" +
                    "      \"Value\": 47.8092,\n" +
                    "      \"Previous\": 47.9628\n" +
                    "    },\n" +
                    "    \"AZN\": {\n" +
                    "      \"ID\": \"R01020A\",\n" +
                    "      \"NumCode\": \"944\",\n" +
                    "      \"CharCode\": \"AZN\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Азербайджанский манат\",\n" +
                    "      \"Value\": 39.6378,\n" +
                    "      \"Previous\": 38.9634\n" +
                    "    },\n" +
                    "    \"GBP\": {\n" +
                    "      \"ID\": \"R01035\",\n" +
                    "      \"NumCode\": \"826\",\n" +
                    "      \"CharCode\": \"GBP\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Фунт стерлингов Соединенного королевства\",\n" +
                    "      \"Value\": 83.3813,\n" +
                    "      \"Previous\": 82.7906\n" +
                    "    },\n" +
                    "    \"AMD\": {\n" +
                    "      \"ID\": \"R01060\",\n" +
                    "      \"NumCode\": \"051\",\n" +
                    "      \"CharCode\": \"AMD\",\n" +
                    "      \"Nominal\": 100,\n" +
                    "      \"Name\": \"Армянских драмов\",\n" +
                    "      \"Value\": 14.2047,\n" +
                    "      \"Previous\": 14.5696\n" +
                    "    },\n" +
                    "    \"BYN\": {\n" +
                    "      \"ID\": \"R01090B\",\n" +
                    "      \"NumCode\": \"933\",\n" +
                    "      \"CharCode\": \"BYN\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Белорусский рубль\",\n" +
                    "      \"Value\": 26.4532,\n" +
                    "      \"Previous\": 26.0031\n" +
                    "    },\n" +
                    "    \"BGN\": {\n" +
                    "      \"ID\": \"R01100\",\n" +
                    "      \"NumCode\": \"975\",\n" +
                    "      \"CharCode\": \"BGN\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Болгарский лев\",\n" +
                    "      \"Value\": 36.4102,\n" +
                    "      \"Previous\": 35.6654\n" +
                    "    },\n" +
                    "    \"BRL\": {\n" +
                    "      \"ID\": \"R01115\",\n" +
                    "      \"NumCode\": \"986\",\n" +
                    "      \"CharCode\": \"BRL\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Бразильский реал\",\n" +
                    "      \"Value\": 13.4639,\n" +
                    "      \"Previous\": 13.2238\n" +
                    "    },\n" +
                    "    \"HUF\": {\n" +
                    "      \"ID\": \"R01135\",\n" +
                    "      \"NumCode\": \"348\",\n" +
                    "      \"CharCode\": \"HUF\",\n" +
                    "      \"Nominal\": 100,\n" +
                    "      \"Name\": \"Венгерских форинтов\",\n" +
                    "      \"Value\": 18.7002,\n" +
                    "      \"Previous\": 18.5618\n" +
                    "    },\n" +
                    "    \"HKD\": {\n" +
                    "      \"ID\": \"R01200\",\n" +
                    "      \"NumCode\": \"344\",\n" +
                    "      \"CharCode\": \"HKD\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Гонконгских долларов\",\n" +
                    "      \"Value\": 85.9932,\n" +
                    "      \"Previous\": 84.5409\n" +
                    "    },\n" +
                    "    \"DKK\": {\n" +
                    "      \"ID\": \"R01215\",\n" +
                    "      \"NumCode\": \"208\",\n" +
                    "      \"CharCode\": \"DKK\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Датских крон\",\n" +
                    "      \"Value\": 95.7082,\n" +
                    "      \"Previous\": 93.7456\n" +
                    "    },\n" +
                    "    \"USD\": {\n" +
                    "      \"ID\": \"R01235\",\n" +
                    "      \"NumCode\": \"840\",\n" +
                    "      \"CharCode\": \"USD\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Доллар США\",\n" +
                    "      \"Value\": 67.3843,\n" +
                    "      \"Previous\": 66.2378\n" +
                    "    },\n" +
                    "    \"EUR\": {\n" +
                    "      \"ID\": \"R01239\",\n" +
                    "      \"NumCode\": \"978\",\n" +
                    "      \"CharCode\": \"EUR\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Евро\",\n" +
                    "      \"Value\": 71.0963,\n" +
                    "      \"Previous\": 70.0662\n" +
                    "    },\n" +
                    "    \"INR\": {\n" +
                    "      \"ID\": \"R01270\",\n" +
                    "      \"NumCode\": \"356\",\n" +
                    "      \"CharCode\": \"INR\",\n" +
                    "      \"Nominal\": 100,\n" +
                    "      \"Name\": \"Индийских рупий\",\n" +
                    "      \"Value\": 88.1714,\n" +
                    "      \"Previous\": 86.4535\n" +
                    "    },\n" +
                    "    \"KZT\": {\n" +
                    "      \"ID\": \"R01335\",\n" +
                    "      \"NumCode\": \"398\",\n" +
                    "      \"CharCode\": \"KZT\",\n" +
                    "      \"Nominal\": 100,\n" +
                    "      \"Name\": \"Казахстанских тенге\",\n" +
                    "      \"Value\": 15.7514,\n" +
                    "      \"Previous\": 15.1626\n" +
                    "    },\n" +
                    "    \"CAD\": {\n" +
                    "      \"ID\": \"R01350\",\n" +
                    "      \"NumCode\": \"124\",\n" +
                    "      \"CharCode\": \"CAD\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Канадский доллар\",\n" +
                    "      \"Value\": 52.5537,\n" +
                    "      \"Previous\": 51.7079\n" +
                    "    },\n" +
                    "    \"KGS\": {\n" +
                    "      \"ID\": \"R01370\",\n" +
                    "      \"NumCode\": \"417\",\n" +
                    "      \"CharCode\": \"KGS\",\n" +
                    "      \"Nominal\": 100,\n" +
                    "      \"Name\": \"Киргизских сомов\",\n" +
                    "      \"Value\": 82.176,\n" +
                    "      \"Previous\": 80.7778\n" +
                    "    },\n" +
                    "    \"CNY\": {\n" +
                    "      \"ID\": \"R01375\",\n" +
                    "      \"NumCode\": \"156\",\n" +
                    "      \"CharCode\": \"CNY\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Китайский юань\",\n" +
                    "      \"Value\": 10.0352,\n" +
                    "      \"Previous\": 99.9646\n" +
                    "    },\n" +
                    "    \"MDL\": {\n" +
                    "      \"ID\": \"R01500\",\n" +
                    "      \"NumCode\": \"498\",\n" +
                    "      \"CharCode\": \"MDL\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Молдавских леев\",\n" +
                    "      \"Value\": 35.9715,\n" +
                    "      \"Previous\": 35.4376\n" +
                    "    },\n" +
                    "    \"NOK\": {\n" +
                    "      \"ID\": \"R01535\",\n" +
                    "      \"NumCode\": \"578\",\n" +
                    "      \"CharCode\": \"NOK\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Норвежских крон\",\n" +
                    "      \"Value\": 72.3233,\n" +
                    "      \"Previous\": 70.4298\n" +
                    "    },\n" +
                    "    \"PLN\": {\n" +
                    "      \"ID\": \"R01565\",\n" +
                    "      \"NumCode\": \"985\",\n" +
                    "      \"CharCode\": \"PLN\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Польский злотый\",\n" +
                    "      \"Value\": 15.1283,\n" +
                    "      \"Previous\": 14.9951\n" +
                    "    },\n" +
                    "    \"RON\": {\n" +
                    "      \"ID\": \"R01585F\",\n" +
                    "      \"NumCode\": \"946\",\n" +
                    "      \"CharCode\": \"RON\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Румынский лей\",\n" +
                    "      \"Value\": 14.3947,\n" +
                    "      \"Previous\": 14.1803\n" +
                    "    },\n" +
                    "    \"XDR\": {\n" +
                    "      \"ID\": \"R01589\",\n" +
                    "      \"NumCode\": \"960\",\n" +
                    "      \"CharCode\": \"XDR\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"СДР (специальные права заимствования)\",\n" +
                    "      \"Value\": 90.6379,\n" +
                    "      \"Previous\": 88.862\n" +
                    "    },\n" +
                    "    \"SGD\": {\n" +
                    "      \"ID\": \"R01625\",\n" +
                    "      \"NumCode\": \"702\",\n" +
                    "      \"CharCode\": \"SGD\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Сингапурский доллар\",\n" +
                    "      \"Value\": 48.6424,\n" +
                    "      \"Previous\": 48.2256\n" +
                    "    },\n" +
                    "    \"TJS\": {\n" +
                    "      \"ID\": \"R01670\",\n" +
                    "      \"NumCode\": \"972\",\n" +
                    "      \"CharCode\": \"TJS\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Таджикских сомони\",\n" +
                    "      \"Value\": 53.929,\n" +
                    "      \"Previous\": 53.0114\n" +
                    "    },\n" +
                    "    \"TRY\": {\n" +
                    "      \"ID\": \"R01700J\",\n" +
                    "      \"NumCode\": \"949\",\n" +
                    "      \"CharCode\": \"TRY\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Турецких лир\",\n" +
                    "      \"Value\": 45.3928,\n" +
                    "      \"Previous\": 44.7598\n" +
                    "    },\n" +
                    "    \"TMT\": {\n" +
                    "      \"ID\": \"R01710A\",\n" +
                    "      \"NumCode\": \"934\",\n" +
                    "      \"CharCode\": \"TMT\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Новый туркменский манат\",\n" +
                    "      \"Value\": 19.2527,\n" +
                    "      \"Previous\": 18.9251\n" +
                    "    },\n" +
                    "    \"UZS\": {\n" +
                    "      \"ID\": \"R01717\",\n" +
                    "      \"NumCode\": \"860\",\n" +
                    "      \"CharCode\": \"UZS\",\n" +
                    "      \"Nominal\": 10000,\n" +
                    "      \"Name\": \"Узбекских сумов\",\n" +
                    "      \"Value\": 60.529,\n" +
                    "      \"Previous\": 59.3424\n" +
                    "    },\n" +
                    "    \"UAH\": {\n" +
                    "      \"ID\": \"R01720\",\n" +
                    "      \"NumCode\": \"980\",\n" +
                    "      \"CharCode\": \"UAH\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Украинских гривен\",\n" +
                    "      \"Value\": 22.267,\n" +
                    "      \"Previous\": 22.4185\n" +
                    "    },\n" +
                    "    \"CZK\": {\n" +
                    "      \"ID\": \"R01760\",\n" +
                    "      \"NumCode\": \"203\",\n" +
                    "      \"CharCode\": \"CZK\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Чешских крон\",\n" +
                    "      \"Value\": 28.9364,\n" +
                    "      \"Previous\": 28.3043\n" +
                    "    },\n" +
                    "    \"SEK\": {\n" +
                    "      \"ID\": \"R01770\",\n" +
                    "      \"NumCode\": \"752\",\n" +
                    "      \"CharCode\": \"SEK\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Шведских крон\",\n" +
                    "      \"Value\": 67.3661,\n" +
                    "      \"Previous\": 67.82\n" +
                    "    },\n" +
                    "    \"CHF\": {\n" +
                    "      \"ID\": \"R01775\",\n" +
                    "      \"NumCode\": \"756\",\n" +
                    "      \"CharCode\": \"CHF\",\n" +
                    "      \"Nominal\": 1,\n" +
                    "      \"Name\": \"Швейцарский франк\",\n" +
                    "      \"Value\": 68.4382,\n" +
                    "      \"Previous\": 67.714\n" +
                    "    },\n" +
                    "    \"ZAR\": {\n" +
                    "      \"ID\": \"R01810\",\n" +
                    "      \"NumCode\": \"710\",\n" +
                    "      \"CharCode\": \"ZAR\",\n" +
                    "      \"Nominal\": 10,\n" +
                    "      \"Name\": \"Южноафриканских рэндов\",\n" +
                    "      \"Value\": 41.9774,\n" +
                    "      \"Previous\": 42.3751\n" +
                    "    },\n" +
                    "    \"KRW\": {\n" +
                    "      \"ID\": \"R01815\",\n" +
                    "      \"NumCode\": \"410\",\n" +
                    "      \"CharCode\": \"KRW\",\n" +
                    "      \"Nominal\": 1000,\n" +
                    "      \"Name\": \"Вон Республики Корея\",\n" +
                    "      \"Value\": 52.9459,\n" +
                    "      \"Previous\": 52.3081\n" +
                    "    },\n" +
                    "    \"JPY\": {\n" +
                    "      \"ID\": \"R01820\",\n" +
                    "      \"NumCode\": \"392\",\n" +
                    "      \"CharCode\": \"JPY\",\n" +
                    "      \"Nominal\": 100,\n" +
                    "      \"Name\": \"Японских иен\",\n" +
                    "      \"Value\": 51.6355,\n" +
                    "      \"Previous\": 50.9247\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
        }


        valutes = new ArrayList<>();

        new JsonTask().execute("https://www.cbr-xml-daily.ru/daily_json.js");

        //new code trough ValuteResourcesParser()
//        {

//            XmlPullParser xpp = getResources().getXml(R.xml.valcurs);
//
//            ValuteResourcesParser parser = new ValuteResourcesParser();
//
//            try {
//
//                if (parser.parse(xpp)) {
//                    Log.d("XML", "parse.status = true ");
//                    valutes = parser.getValutes();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    // end xml parsing








        txtCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    Integer userInput = Integer.valueOf(String.valueOf(txtCurrent.getText()));
                    Double finalValue = Double.valueOf(userInput)/currentValue*currentNominal;
                    DecimalFormat decimalFormat = new DecimalFormat("#.####");
                    txtFinal.setText(String.valueOf(decimalFormat.format(finalValue)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listValutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentNominal = adapter.getItem(i).getNominal();
                currentValue = adapter.getItem(i).getValue();
                txtCurrentValuteName.setText( adapter.getItem(i).getName());
                textViewfinalCharCode.setText("("+adapter.getItem(i).getCharCode()+")");
                txtCurrent.setText("");
                txtFinal.setText("");
            }
        });

    }



    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }


            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(content);

                JSONObject inner =  jsonObject.getJSONObject("Valute");
                Iterator<String> keys = inner.keys();
                while (keys.hasNext()){
                    currentValute = new Valute();
                    String key = keys.next();
                    JSONObject actualValute = inner.getJSONObject(key);
                    currentValute.setName(actualValute.getString("Name"));
                    currentValute.setCharCode(actualValute.getString("CharCode"));
                    currentValute.setNominal(actualValute.getInt("Nominal"));
                    currentValute.setValue(actualValute.getDouble("Value"));
                    valutes.add(currentValute);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new ValutesAdapter(getApplicationContext(), valutes);
            listValutes.setAdapter(adapter);
        }
    }


}