package com.euphoria.selfcare.euphoria;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowMapRSD extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

    private final LatLng RSJM = new LatLng(-7.286305613523692, 112.76253558374542);
    private final LatLng RSHU = new LatLng(-7.264586155125942, 112.75608968137871);
    private final LatLng UPP = new LatLng(-7.2725009, 112.7570406);
    private final LatLng LPD = new LatLng(-7.3175143, 112.7199936);
    private final LatLng PLP = new LatLng(-7.2852696, 112.7431798);
    private final LatLng RSUD = new LatLng(-7.2682011, 112.7558776);
    private final LatLng RSPSP = new LatLng(-7.3044826, 112.7630603);
    private final LatLng RSD = new LatLng(-7.2874927, 112.7367508);

    private Marker markerRSJM;
    private Marker markerRSHU;
    private Marker markerUPP, markerLPD, markerPLP, markerRSUD, markerRSPSP, markerRSD;
    float zoomLevel = 18.0f; //This goes up to 21

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map1);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().hide();
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        // Add some markers to the map, and add a data object to each marker.
        markerRSJM = map.addMarker(new MarkerOptions()
                .position(RSJM)
                .title("RSJ Menur"+
                        "Jl. Raya Menur No.120, Gubeng, Surabaya"));
        markerRSJM.setTag(0);

        markerRSHU = map.addMarker(new MarkerOptions()
                .position(RSHU)
                .title("RS Husada Utama"));
        markerRSHU.setTag(0);

        markerUPP = map.addMarker(new MarkerOptions()
                .position(UPP)
                .title("Unit Pelayanan Psikologi UNAIR"));
        markerUPP.setTag(0);

        markerLPD = map.addMarker(new MarkerOptions()
                .position(LPD)
                .title("Lembaga Psikologi Dr. Soetomo"));
        markerLPD.setTag(0);

        markerPLP = map.addMarker(new MarkerOptions()
                .position(PLP)
                .title("-7.2852696, 112.7431798"));
        markerPLP.setTag(0);

        markerRSUD = map.addMarker(new MarkerOptions()
                .position(RSUD)
                .title("RSUD Dr. Soetomo"));
        markerRSUD.setTag(0);

        markerRSPSP = map.addMarker(new MarkerOptions()
                .position(RSPSP)
                .title("RS Premier Surabaya"));
        markerRSPSP.setTag(0);

        markerRSD = map.addMarker(new MarkerOptions()
                .position(RSD)
                .title("RS Darmo"));
        markerRSD.setTag(0);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(RSD, zoomLevel));
        map.setOnMarkerClickListener(this);
        /** Called when the user clicks a marker. */
    }
    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(markerRSJM))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("RSJ Menur");
            dialog.setMessage("Jl. Raya Menur No.120, Gubeng, Surabaya"+"\n(031) 5021635");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
        if (marker.equals(markerRSHU))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("RS Husada Utama");
            dialog.setMessage("Jl. Mayjen Prof. Dr. Moestopo No.31-35"+"\n(031) 5018335");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
        if (marker.equals(markerUPP))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Unit Pelayanan Psikologi UNAIR");
            dialog.setMessage("Lt. 3 Fakultas Psikologi UNAIR, Jl. Dharmawangsa No.4-6"+"\n(031) 5047251");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
        if (marker.equals(markerLPD))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Lembaga Psikologi Dr. Soetomo");
            dialog.setMessage("Jl. Ketintang Madya II No.1"+"\n(031) 8290211");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
        if (marker.equals(markerPLP))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Pusat Layanan Psikologi");
            dialog.setMessage("Jl. Dinoyo, No. 42-44"+"\n(031) 5678478");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
        if (marker.equals(markerRSUD))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("RSUD Dr. Soetomo");
            dialog.setMessage("Jl. Mayjen Prof. Dr. Moestopo No.6-8, Airlangga, Gubeng, Surabaya"+"\n(031) 5501078");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
        if (marker.equals(markerRSPSP))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("RS Premier Surabaya");
            dialog.setMessage("Jl. Nginden Intan Barat Blok B, Ngenden Jangkungan, Sukolilo, Surabaya"+"\n(031) 5993211");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
        if (marker.equals(RSD))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("RS Darmo");
            dialog.setMessage("Jl. Raya Darmo 90, Surabaya"+"\n(031) 5676253 – 6");
            dialog.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }

        return false;
    }
}