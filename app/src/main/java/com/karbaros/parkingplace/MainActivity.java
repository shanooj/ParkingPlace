package com.karbaros.parkingplace;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.ui.IconGenerator;
import com.karbaros.parkingplace.model.ParkSpot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnMarkerClickListener {

    List<LatLng> points = new ArrayList<>();
    public List<List<LatLng>> allPoints = new ArrayList<List<LatLng>>();
    List<LatLng> points2 = new ArrayList<>();
    GoogleMap mMap;
    MapFragment mMapFragment;
    FrameLayout fl;
    TextView res;
    static Marker mapMarker = null;
    ConstraintLayout rootLayout;
    private DatabaseReference mDatabase;
    private DatabaseReference mParkSpot;
    private ParkSpot parkSpot;
    private List<ParkSpot> parkSpots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout) findViewById(R.id.frmL);
        res = (TextView) findViewById(R.id.tvresult);
        rootLayout = (ConstraintLayout) findViewById(R.id.root);
        initMap();
       /* points.add(new LatLng(37.35, -122.0));
        points.add(new LatLng(37.45, -122.0));
        points.add(new LatLng(37.45, -122.2));
        points.add(new LatLng(37.35, -122.2));*/
        points2.add(new LatLng(13.025588, 77.587338));
        points2.add(new LatLng(13.125588, 77.587338));
        points2.add(new LatLng(13.125588, 77.787338));
        points2.add(new LatLng(13.025588, 77.787338));


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mParkSpot = mDatabase.child("parkSpot");
        /*String userId = mParkSpot.push().getKey();

        ParkSpot spot = new ParkSpot(userId, points2, "spot2");
        mParkSpot.child(userId).setValue(spot);*/
        Log.i("spots", "oncreate");
        mParkSpot.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("spots", "oncreate firebase");
                for (DataSnapshot snapShotParkSpots : dataSnapshot.getChildren()) {
                    Log.i("spotsdata", dataSnapshot.toString());
                    parkSpot = snapShotParkSpots.getValue(ParkSpot.class);
                    parkSpots.add(parkSpot);
                    //  Log.i("spots", parkSpot.getLocationPoints().size() + "");

                    Log.i("spotsnumber", parkSpots.size() + "");
                }
                drawPolygon();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initMap() {
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.frmntMap);
        mMapFragment.getMapAsync(this);
    }

    private void drawPolygon() {
        for (int i = 0; i < parkSpots.size(); i++) {
            List<LatLng> p = new ArrayList<>();
            for (HashMap<String, Object> map : parkSpots.get(i).getLocationPoints()) {

                p.add(new LatLng(Double.parseDouble(map.get("latitude").toString()), Double.parseDouble(map.get("longitude").toString())));
                Log.i("spots", map.get("latitude").toString());
            }
            Log.i("spots_leng", p.size() + "");
            allPoints.add(p);
            Log.i("spots_leng21", allPoints.get(0).size() + "");
            Polygon polygon = mMap.addPolygon(new PolygonOptions().addAll(p));
            //points.clear();


        }
        Log.i("spots_leng25", allPoints.size() + "");
        Log.i("spots_leng2", allPoints.get(0).size() + "");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Instantiates a new Polygon object and adds points to define a rectangle


// Get back the mutable Polygon
        Log.i("spots", "points draw");
        if (points.size() != 0) {
            Polygon polygon = mMap.addPolygon(new PolygonOptions().addAll(points));
        }


        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onCameraMoveStarted(int reason) {

       /* if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(this, "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(this, "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(this, "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onCameraMove() {
        //  Toast.makeText(this, "The camera is moving.",
        //  Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveCanceled() {
      /*  Toast.makeText(this, "Camera movement canceled.",
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onCameraIdle() {

        Toast.makeText(this, "The camera has stopped moving.", Toast.LENGTH_SHORT).show();
        Log.i("spotslength", allPoints.size() + "");
        Log.i("spot_L1", allPoints.get(0).size() + "");
        Log.i("spot_L2", allPoints.get(1).size() + "");

        //Toast.makeText(this, "Latitude:"+ mMap.getCameraPosition().target.latitude+"Longitude:"+mMap.getCameraPosition().target.longitude,Toast.LENGTH_SHORT).show();
        for (int i = 0; i < allPoints.size(); i++) {
            Log.i("spotslength", allPoints.get(i).size() + "");
            boolean contain = PolyUtil.containsLocation(mMap.getCameraPosition().target, allPoints.get(i), true);
            if (contain) {
                if (mapMarker == null) {
                    MarkerOptions options = new MarkerOptions();
                    IconGenerator iconFactory = new IconGenerator(MainActivity.this);
                    iconFactory.setRotation(0);
                    iconFactory.setBackground(null);

                    View view = View.inflate(MainActivity.this, R.layout.map_pin, null);
                    iconFactory.setContentView(view);

                    options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon()));
                    options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                    options.position(mMap.getCameraPosition().target);


                    mapMarker = mMap.addMarker(options);

                }
                break;
            } else {
                if (mapMarker != null) {
                    mapMarker.remove();
                    mapMarker = null;

                }
            }
        }


        //Toast.makeText(this, "contains : " + contain, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Snackbar snackbar = Snackbar.make(rootLayout, "Start booking.....", Snackbar.LENGTH_LONG);

        snackbar.show();
        return false;
    }
}
