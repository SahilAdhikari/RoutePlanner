package com.example.mymaplearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mymaplearning.databinding.ActivityMainBinding;
import com.example.mymaplearning.databinding.ActivityTravelBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TravelActivity extends AppCompatActivity {
    //Intent Recieve

    public ArrayList<Address> myStopIntent;

    //ListView
    ActivityTravelBinding binding;
    private ListView optmisedListView;
    private ArrayList<String> myStops;
    private ArrayAdapter<String> arrayAdapter;
    ArrayList<TravelId> travelIdArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTravelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        myStopIntent = new ArrayList<>();
        myStopIntent = (ArrayList<Address>) args.getSerializable("ARRAYLIST");
        init();
    }
    public void init(){
       travelIdArrayList = new ArrayList<>();
       int n = myStopIntent.size();
       double adj[][] = new double[n][n];

       ArrayList<String> addressLine = new ArrayList<>();
       for(int i=0;i<n;i++){
           addressLine.add(myStopIntent.get(i).getAddressLine(0));
           String mainAddress = myStopIntent.get(i).getAddressLine(0);

           String subAddress = myStopIntent.get(i).getCountryName();
           double lat = myStopIntent.get(i).getLatitude();
           double longi = myStopIntent.get(i).getLongitude();
           travelIdArrayList.add(new TravelId(mainAddress,subAddress,lat,longi));

       }

        optmisedListView = (ListView)findViewById(R.id.myFinalList);


       for(int i=0;i<n;i++){
           for(int j=0;j<n;j++){
               if(i==j){
                   adj[i][j]=0;
                   continue;
               }
               if(adj[i][j]==0){
                   double lat1 = myStopIntent.get(i).getLatitude();
                   double lat2 = myStopIntent.get(j).getLatitude();
                   double lon1 = myStopIntent.get(i).getLongitude();
                   double lon2 = myStopIntent.get(i).getLongitude();

                   Double dis = distance(lat1,lat2,lon1,lon2);
                   adj[i][j] = dis;
                   adj[j][i] = dis;


               }


           }
       }
       TSP tsp = new TSP(n,adj);
       int a[] = tsp.final_path;
       Map<String,Integer> map = new HashMap<>();
       for(int i=0;i<n;i++){
           map.put(addressLine.get(i),i);
       }
       //Log.i("Hello",String.valueOf(addressLine.size()));
       ArrayList<String> finalList = new ArrayList<>();
       ArrayList<TravelId> tempTravelIdArrayList = new ArrayList<>(travelIdArrayList);
       travelIdArrayList = new ArrayList<>();
       for(int i=0;i<n;i++){
           int x = a[i];
           finalList.add(addressLine.get(a[x]));
           travelIdArrayList.add(tempTravelIdArrayList.get(a[x]));
       }
//        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,finalList);
//        optmisedListView.setAdapter(arrayAdapter);
//
//        optmisedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Address curr = myStopIntent.get(map.get(finalList.get(position)));
//                double lat = curr.getLatitude();
//                double longi = curr.getLongitude();
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?daddr="+lat+","+longi));
//                startActivity(intent);
//            }
//        });


        ListAdapter listAdapter = new ListAdapter(TravelActivity.this,travelIdArrayList);
        binding.myFinalList.setAdapter(listAdapter);
            binding.myFinalList.setClickable(true);
        binding.myFinalList.setOnItemClickListener((parent, view, position, id) -> {
            Log.e("hello","I am clicked");
            TravelId myTravelid =  travelIdArrayList.get(position);
            double lat = myTravelid.lat;
            double longi = myTravelid.longi;
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr="+lat+","+longi));
//            Intent intent = new Intent(TravelActivity.this,MainActivity.class);
            startActivity(intent);
        });


    }
    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }
}