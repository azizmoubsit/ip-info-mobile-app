package ma.enset.moubsit_aziz_exam_m1_iibdcc_23;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String latLong;
    private MapView mapView;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String Lat = latLong.split(",")[0];
        String Long = latLong.split(",")[1];
        LatLng location = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Long));
        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        Intent intent = getIntent();
        latLong = intent.getStringExtra("LatLong");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
