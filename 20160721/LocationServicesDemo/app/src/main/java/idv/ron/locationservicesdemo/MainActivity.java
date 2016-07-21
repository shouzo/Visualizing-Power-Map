package idv.ron.locationservicesdemo;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_RESOLUTION = 1;
    private final static String TAG = "MainActivity";
    private GoogleApiClient googleApiClient;
    private Location lastLocation;


    private LocationListener locationListener = new LocationListener() {
        @Override
        // 當位置改變時，系統會自動呼叫此方法並傳入新的位置(Location 物件)
        public void onLocationChanged(Location location) {
            updateLastLocationInfo(location);
            lastLocation = location;
        }
    };


    private GoogleApiClient.ConnectionCallbacks connectionCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {
                // 呼叫 GoogleApiClient.connect()後，一旦連結成功系統會呼叫此方法
                @Override
                public void onConnected(Bundle bundle) {
                    Log.i(TAG, "GoogleApiClient connected");

                    // 取得最新位置
                    lastLocation = LocationServices.FusedLocationApi
                            .getLastLocation(googleApiClient);


                    // 設定定位的優先序
                    LocationRequest locationRequest = LocationRequest.create()
                            // 高精準度方式定位︰GPS
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            // 設定間隔多久查詢一次最新位置，單位︰毫秒(ms)
                            .setInterval(10000)
                            // 設定距離上次定位達到多少公尺，才代表位置更新
                            .setSmallestDisplacement(1000);
                            // 註冊 requestLocationUpdates 監聽位置是否改變
                    LocationServices.FusedLocationApi.requestLocationUpdates(
                            googleApiClient, locationRequest, locationListener);
                }


                // 當暫時無法連線時，系統會呼叫此方法
                @Override
                public void onConnectionSuspended(int i) {
                    showToast(R.string.msg_GoogleApiClientConnectionSuspended);
                }
            };


    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                // 當連結失敗，系統會呼叫此方法
                @Override
                public void onConnectionFailed(ConnectionResult result) {
                    showToast(R.string.msg_GoogleApiClientConnectionFailed);
                    // 如果沒有解決方案，直接呈現錯誤訊息
                    if (!result.hasResolution()) {
                        GooglePlayServicesUtil.getErrorDialog(
                                result.getErrorCode(),
                                MainActivity.this,
                                0
                        ).show();
                        return;
                    }
                    try {
                        // 有解決方案則開啟新的 Activity 引導使用者解決。
                        // 之後系統會呼叫 onActivityResult()，並傳遞 request code 過去
                        result.startResolutionForResult(
                                MainActivity.this,
                                REQUEST_CODE_RESOLUTION);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Exception while starting resolution activity");
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient == null) {
            // 取得 GoogleApiClient 並且註冊 ConnectionCallbacks 與 ConnectionFailedListener
            // 無論連結 API 功能是否成功都會呼叫實作好的對應方法
            googleApiClient = new GoogleApiClient.Builder(this)
                    // 指定使用 LocationServices API 功能
                    .addApi(LocationServices.API)
                    // 註冊 ConnectionCallbacks 以監聽連結事件
                    .addConnectionCallbacks(connectionCallbacks)
                    // 註冊 OnConnectionFailedListener 以監聽連結失敗事件
                    .addOnConnectionFailedListener(onConnectionFailedListener)
                    .build();
        }
        // GoogleApiClient 物件必須先呼叫 connect() 必且完成連結方能開始使用 API 功能
        googleApiClient.connect();
    }


    // 進入 pause 狀態就解除對指定 API 功能的連結
    @Override
    protected void onPause() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onPause();
    }


    // 當初呼叫 ConnectionResult.startResolutionForResult() 會開啟新的 Activity 引導使用者解決
    // 之後系統會呼叫此方法，並傳遞 request code 過來
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 如果是 RESULT_OK 代表錯誤已經被修正，所以可以重新連結指定 API 的功能
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_RESOLUTION) {
                googleApiClient.connect();
            }
        }
    }


    /* 將位置資訊呈現在 TextView 上 */
    private void updateLastLocationInfo(Location lastLocation) {
        TextView tvLastLocation = (TextView) findViewById(R.id.tvLastLocation);
        String message = "";
        message += "The Information of the Last Location \n";

        if (lastLocation == null) {
            showToast(R.string.msg_LastLocationNotAvailable);
            return;
        }


        /* 取得定位的日期和時間 */
        Date date = new Date(lastLocation.getTime());
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String time = dateFormat.format(date);
        message += "fix time: " + time + "\n";


        /* 取得本身位置的緯度和經度 */
        message += "latitude: " + lastLocation.getLatitude() + "\n";
        message += "longitude: " + lastLocation.getLongitude() + "\n";

        /* 取得定位的精準度 */
        message += "accuracy (meters): " + lastLocation.getAccuracy() + "\n";

        /* 取得本身位置的高度 */
        message += "altitude (meters): " + lastLocation.getAltitude() + "\n";

        /* 取得方向 */
        message += "bearing (horizontal direction- in degrees): "
                + lastLocation.getBearing() + "\n";

        /* 取得速度 */
        message += "speed (meters/second): " + lastLocation.getSpeed() + "\n";

        tvLastLocation.setText(message);
    }


    /* 按下 Distance 按鈕會計算 2 點間距離 */
    public void onDistanceClick(View view) {
        EditText etLocationName = (EditText) findViewById(R.id.etLocationName);
        TextView tvDistance = (TextView) findViewById(R.id.tvDistance);
        String locationName = etLocationName.getText().toString().trim();

        if (!lastLocationFound() || !inputValid(locationName)) {
            return;
        }

        Address address = getAddress(locationName);
        if (address == null) {
            showToast(R.string.msg_LocationNotAvailable);
            return;
        }


        // 計算裝置本身位置與使用者輸入位置，此 2 點間的距離(公尺)，結果會自動存入 results[0]
        float[] results = new float[1];
        Location.distanceBetween(lastLocation.getLatitude(),
                lastLocation.getLongitude(), address.getLatitude(),
                address.getLongitude(), results);
        String text = String.format(
                "the distance between the last location and %s is %.2f meter(s).",
                locationName,
                results[0]
        );
        tvDistance.setText(text);
    }


    /* 按下 Direct 按鈕會導航到使用者輸入的位置 */
    public void onDirectClick(View view) {
        EditText etLocationName = (EditText) findViewById(R.id.etLocationName);
        String locationName = etLocationName.getText().toString().trim();

        if (!lastLocationFound() || !inputValid(locationName)) {
            return;
        }

        Address address = getAddress(locationName);
        if (address == null) {
            showToast(R.string.msg_LocationNotAvailable);
            return;
        }


        // 取得裝置本身位置與使用者輸入位置的經緯度
        double fromLat = lastLocation.getLatitude();
        double fromLng = lastLocation.getLongitude();
        double toLat = address.getLatitude();
        double toLng = address.getLongitude();

        direct(fromLat, fromLng, toLat, toLng);
    }


    /* 將使用者輸入的地名或地址轉成 Address 物件 */
    private Address getAddress(String locationName) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;

        try {
            // 解譯地名/地址後可能產生多筆位置資訊，但限定回傳 1 筆
            addressList = geocoder.getFromLocationName(locationName, 1);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        if (addressList == null || addressList.isEmpty()) {
            return null;
        } else {
            // 因為當初限定只回傳 1 筆，所以只要取得第 1 個 Address 物件即可
            return addressList.get(0);
        }
    }


    // 開啟內建 Google 地圖應用程式來完成導航要求
    private void direct(double fromLat, double fromLng, double toLat,
                        double toLng) {

        // 設定欲前往的 Uri ，saddr︰出發地經緯度、daddr︰目的地經緯度
        String uriStr = String.format(Locale.US,
                "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", fromLat,
                fromLng, toLat, toLng);
        Intent intent = new Intent();

        // 指定交由 Google 地圖應用程式接手
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");

        // ACTION_VIEW︰呈現資料給使用者觀看
        intent.setAction(android.content.Intent.ACTION_VIEW);

        // 將 Uri 資訊附加到 Intent 物件上
        intent.setData(Uri.parse(uriStr));
        startActivity(intent);
    }


    /* 檢查是否已經取得裝置本身位置 */
    private boolean lastLocationFound() {
        if (lastLocation == null) {
            showToast(R.string.msg_LocationNotAvailable);
            return false;
        }
        return true;
    }


    /* 檢查是否輸入資料 */
    private boolean inputValid(String input) {
        if (input == null || input.length() <= 0) {
            showToast(R.string.msg_InvalidInput);
            return false;
        }
        return true;
    }

    private void showToast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        askPermissions();
    }

    private static final int REQ_PERMISSIONS = 0;

    // New Permission see Appendix A
    private void askPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    REQ_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        String text = getString(R.string.text_ShouldGrant);
                        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }

                break;
        }
    }
}