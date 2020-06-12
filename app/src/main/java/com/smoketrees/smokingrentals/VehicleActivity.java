package com.smoketrees.smokingrentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VehicleActivity extends AppCompatActivity {

    private Spinner vehicleSpinner;
    private int selectedVehicle;
    private EditText regNo, city, state, street, locality;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ImageButton openMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        initialise();
        setVehicleSpinner();
        getCurrentLocation();

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment=new MapFragment();
                mapFragment.show(getSupportFragmentManager(),
                        mapFragment.getClass().getSimpleName());
            }
        });

    }

    private void setVehicleSpinner(){

        ArrayList<VehicleSpinnerItem> vehicleList = new ArrayList<>();
        vehicleList.add(new VehicleSpinnerItem(R.drawable.bike));
        vehicleList.add(new VehicleSpinnerItem(R.drawable.bicycle));
        vehicleList.add(new VehicleSpinnerItem(R.drawable.car));
        vehicleList.add(new VehicleSpinnerItem(R.drawable.suv));
        vehicleList.add(new VehicleSpinnerItem(R.drawable.scooter));

        VehicleAdapter adapter = new VehicleAdapter(this, vehicleList);
        vehicleSpinner.setAdapter(adapter);

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleSpinnerItem clickedItem= (VehicleSpinnerItem)parent.getItemAtPosition(position);
                selectedVehicle=clickedItem.getImage();

                if(selectedVehicle==R.drawable.bicycle)
                    regNo.setVisibility(View.GONE);
                else
                    regNo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }
        });
    }

    void getLocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if(location!=null){
                    try {
                        Geocoder geocoder=new Geocoder(VehicleActivity.this, Locale.getDefault());
                        List<Address> addresses=geocoder.getFromLocation(
                                location.getLatitude(),
                                location.getLongitude(),
                                1
                        );
                        displayAddress(addresses);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    void displayAddress(List<Address> addressList){
        city.setText(addressList.get(0).getLocality());
        state.setText(addressList.get(0).getAdminArea());
        locality.setText(addressList.get(0).getSubLocality());
        street.setText(addressList.get(0).getFeatureName());
    }

    private void initialise(){
        vehicleSpinner=findViewById(R.id.vehicle_spinner);
        regNo=findViewById(R.id.regNo);
        openMap=findViewById(R.id.openMap);
        street=findViewById(R.id.street);
        locality=findViewById(R.id.locality);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);
    }

    private void getCurrentLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(VehicleActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getLocation();
        }else{
            ActivityCompat.requestPermissions(VehicleActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
}
