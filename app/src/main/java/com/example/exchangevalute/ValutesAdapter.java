package com.example.exchangevalute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ValutesAdapter extends BaseAdapter {

LayoutInflater inflater;
ArrayList<Valute> valutes;

    public ValutesAdapter(Context context,ArrayList<Valute> valutes) {
        this.inflater = LayoutInflater.from(context);
        this.valutes = valutes;
    }

    @Override
    public int getCount() { return valutes.size();}

    @Override
    public Valute getItem(int pos) {
        return this.valutes.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return this.valutes.get(pos).hashCode();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final  Valute valute = getItem(pos);

        if(convertView==null){
            convertView = inflater.inflate(R.layout.list_view_valutes,parent,false);
ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewCharCode = (TextView) convertView.findViewById(R.id.textViewCharCode);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            viewHolder.textViewNominal = (TextView) convertView.findViewById(R.id.textViewNominal);
            viewHolder.textViewValue = (TextView) convertView.findViewById(R.id.textViewValue);
            convertView.setTag(viewHolder);
        }
 ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textViewValue.setText(String.valueOf( valute.getValue())+"(RUB)");
        viewHolder.textViewName.setText(valute.getName());
        viewHolder.textViewNominal.setText(String.valueOf( valute.getNominal()));
        viewHolder.textViewCharCode.setText( valute.getCharCode());

        return convertView;

    }
}
