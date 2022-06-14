package com.example.mymaplearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<TravelId> {


    public ListAdapter(Context context, ArrayList<TravelId> travelIdArrayList){

        super(context,R.layout.item_stops, travelIdArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TravelId travelId = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stops,parent,false);

        }

        TextView mainAddressTextView = convertView.findViewById(R.id.mainAddressId);
        TextView subAddressTextView = convertView.findViewById(R.id.subAddressId);
        Button doSomethingbutton = convertView.findViewById(R.id.buttonId);

        if(travelId.mainAddress!=null)mainAddressTextView.setText(travelId.mainAddress);
        if(travelId.subAddress!=null)subAddressTextView.setText(travelId.subAddress);
//        doSomethingbutton.
        return convertView;
    }
}
