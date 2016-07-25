package com.example.shouzo.locationlistener;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    /* 變數宣告 */
    static final int MIN_TIME = 5000;   // 位置更新條件，5000 毫秒 (= 5秒)
    static final float MIN_DIST = 5;// 位置更新條件：5 公尺
    LocationManager mgr;                // 定位管理員
    TextView txv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView) findViewById(R.id.txv);
        mgr = (LocationManager) getSystemService(LOCATION_SERVICE); // 取得系統服務
    }

    protected void onResume() {
        super.onResume();

        /* 取得最佳的定位提供者 */
        String best = mgr.getBestProvider(new Criteria(), true);

        if (best != null) {     // 如果有定位提供者可用
            txv.setText("取得定位資訊中...");
            mgr.requestLocationUpdates(best, MIN_TIME, MIN_DIST, this);
        } else                    // 如果沒有提供者，顯示提示訊息
            txv.setText("請確認已開啟定位功能！");
    }

    protected void onPause() {
        super.onPause();
        mgr.removeUpdates(this);// 取消註冊更新事件
    }

    public void onLocationChange(Location location) {
        String str = "定位提供者：" + location.getProvider();
        str += String.format("\n緯度：%.5f\n經度：%.5f\n高度：%.2f公尺",
                location.getLatitude(),     // 緯度
                location.getLongitude(),    // 經度
                location.getAltitude());    // 高度
        txv.setText(str);
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnable(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void setup(View v) {
        // Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        // startActivity(it);
        // startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);

        startActivity(viewIntent);
    }
}