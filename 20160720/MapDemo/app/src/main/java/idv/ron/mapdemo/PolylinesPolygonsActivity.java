package idv.ron.mapdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class PolylinesPolygonsActivity extends AppCompatActivity implements
        OnMapReadyCallback {
    private GoogleMap map;
    private Marker marker_taroko;
    private Marker marker_yushan;
    private Marker marker_kenting;
    private Marker marker_yangmingshan;
    private LatLng taroko;
    private LatLng yushan;
    private LatLng kenting;
    private LatLng yangmingshan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polyline_polygon_activity);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fmMap);
        mapFragment.getMapAsync(this);
        initPoints();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        setUpMap();
    }

    private void initPoints() {
        taroko = new LatLng(24.151287, 121.625537);
        yushan = new LatLng(23.791952, 120.861379);
        kenting = new LatLng(21.985712, 120.813217);
        yangmingshan = new LatLng(25.091075, 121.559834);
    }

    private void setUpMap() {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(taroko)
                .zoom(7)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        addMarkersToMap();

        map.setInfoWindowAdapter(new MyInfoWindowAdapter());

        MyMarkerListener myMarkerListener = new MyMarkerListener();
        map.setOnMarkerClickListener(myMarkerListener);
        map.setOnInfoWindowClickListener(myMarkerListener);

        addPolylinesPolygonsToMap();
    }

    private void addMarkersToMap() {
        marker_taroko = map.addMarker(new MarkerOptions()
                .position(taroko)
                .title(getString(R.string.marker_title_taroko))
                .snippet(getString(R.string.marker_snippet_taroko))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        marker_yushan = map.addMarker(new MarkerOptions().position(yushan)
                .title(getString(R.string.marker_title_yushan))
                .snippet(getString(R.string.marker_snippet_yushan))
                .draggable(true));

        marker_kenting = map.addMarker(new MarkerOptions().position(kenting)
                .title(getString(R.string.marker_title_kenting))
                .snippet(getString(R.string.marker_snippet_kenting))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        marker_yangmingshan = map.addMarker(new MarkerOptions()
                .position(yangmingshan)
                .title(getString(R.string.marker_title_yangmingshan))
                .snippet(getString(R.string.marker_snippet_yangmingshan))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    private void addPolylinesPolygonsToMap() {
        Polyline polyline = map.addPolyline(
                new PolylineOptions()
                        .add(yushan, yangmingshan, taroko)
                        .width(5)
                        .color(Color.MAGENTA)
                        .zIndex(1));

        polyline.setWidth(6);

        map.addPolygon(
                new PolygonOptions()
                        .add(yushan, taroko, kenting)
                        .strokeWidth(5)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.argb(200, 100, 150, 0)));

        map.addCircle(
                new CircleOptions()
                        .center(yushan)
                        .radius(100000)
                        .strokeWidth(5)
                        .strokeColor(Color.TRANSPARENT)
                        .fillColor(Color.argb(100, 0, 0, 100)));
    }


    private class MyMarkerListener implements OnMarkerClickListener,
            OnInfoWindowClickListener {
        @Override
        public boolean onMarkerClick(final Marker marker) {
            return false;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(PolylinesPolygonsActivity.this, marker.getTitle(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private class MyInfoWindowAdapter implements InfoWindowAdapter {
        private final View infoWindow;

        MyInfoWindowAdapter() {
            infoWindow = View.inflate(
                    PolylinesPolygonsActivity.this,
                    R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            int logoId;
            if (marker.equals(marker_yangmingshan)) {
                logoId = R.drawable.logo_yangmingshan;
            } else if (marker.equals(marker_taroko)) {
                logoId = R.drawable.logo_taroko;
            } else if (marker.equals(marker_yushan)) {
                logoId = R.drawable.logo_yushan;
            } else if (marker.equals(marker_kenting)) {
                logoId = R.drawable.logo_kenting;
            } else {
                logoId = 0;
            }

            ImageView iv_logo = ((ImageView) infoWindow
                    .findViewById(R.id.ivLogo));
            iv_logo.setImageResource(logoId);

            String title = marker.getTitle();
            TextView tv_title = ((TextView) infoWindow
                    .findViewById(R.id.tvTitle));
            tv_title.setText(title);

            String snippet = marker.getSnippet();
            TextView tv_snippet = ((TextView) infoWindow
                    .findViewById(R.id.tvSnippet));
            tv_snippet.setText(snippet);

            return infoWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    public void onClearMapClick(View view) {
        map.clear();
    }

    public void onResetMapClick(View view) {
        map.clear();
        addMarkersToMap();
        addPolylinesPolygonsToMap();
    }

}
