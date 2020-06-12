package com.smoketrees.smokingrentals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VehicleAdapter extends ArrayAdapter<VehicleSpinnerItem> {

    public VehicleAdapter(Context context, ArrayList<VehicleSpinnerItem> vehicleList){
        super(context, 0, vehicleList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.vehicle_spinner_item, parent, false
            );
        }
        ImageView vehicleImage=convertView.findViewById(R.id.vehicle);

        VehicleSpinnerItem currentItem=getItem(position);

        assert currentItem != null;
        vehicleImage.setImageResource(currentItem.getImage());

        return convertView;
    }
}
