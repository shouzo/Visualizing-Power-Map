# 20160711 - 新增測試用 Google Map App    

## 本日進度
- [x] 一、新增測試用 Google Map App
	[x]（一）新增 Google Map App 專案
	[x]（二）啟動 Google Map

- 參考資料
  	**[Android Tutorial 第四堂（2）設計地圖應用程式 - Google Maps Android API v2](http://www.codedata.com.tw/mobile/android-tutorial-the-4th-class-google-maps-android-api-v2/)**


## 一、新增測試用 Google Map App
### (一) 新增 Google Map App 專案
* 檔案說明：
	* googlemapsapi.xml：儲存與設定 Google Maps API Key 的資源檔案。
	* MapsActivity.java：地圖元件。
	* activity_maps.xml：地圖元件使用的畫面資源檔。
1. 在 googlemapsapi.xml中加入 API Key：
![](/20160711/assets/1.png)
	* API Key：**AIzaSyCDh21DwDgDg3Ty70uSkn_jhgLNXUvumT8**
2. 加入Google Play Service SDK的設定：
	* Gradle Scripts -> build.gradle(Module:app)：在「dependencies」區塊。
3. 加入讀取位置的授權：
	* AndroidManifest.xml：在<manifest>標籤下加入讀取位置的授權。
4. 加入Google Service版本、Google Map API key與地圖元件的設定：
	* AndroidManifest.xml：在<application>標籤下加入Google Service版本、Google Map API key與地圖元件的設定。


### (二) 啟動 Google Map
1. 設定地圖顯示的位置(經緯度)
* 地點：台北101
* 座標：25.033408, 121.564099
2. 新增地圖標記(變更預設圖樣)


[預覽畫面]
![](/20160711/assets/Screenshot_20160711-160546.png)

![](/20160711/assets/Screenshot_20160711-160555.png)

![](/20160711/assets/Screenshot_20160711-160605.png)

![](/20160711/assets/Screenshot_20160711-160626.png)




