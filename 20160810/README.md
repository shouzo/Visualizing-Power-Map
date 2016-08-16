# 20160810 - 將在 Google API 網頁上顯示的 JSON 資料回傳到 APP 上    

* 參考資料：
1. [Google Maps API Web Service](https://developers.google.com/maps/web-services/overview)
2. Network Connection
		(1) [Android - Network Connection Tutorial](http://www.tutorialspoint.com/android/android_network_connection.htm)
3. How to Parse JSON？ 
		(1) [Android - JSON Parser Tutorial](http://www.tutorialspoint.com/android/android_json_parser.htm)
		(2) [JSON Parsing in Android using Android Studio](http://mobilesiri.com/json-parsing-in-android-using-android-studio/)
		(3) [JSON Parsing, JSONObjects & JsonArrays, HttpUrlConnection - Android Studio](https://www.youtube.com/watch?v=frltqnSKqiA&index=11&list=PLgjt1h_kabFebUn1anGYZ6_Tfkw64Ww12)
		(4) [JSON Parsing, Creating a URLConnection - Android Studio](https://www.youtube.com/watch?v=Gyaay7OTy-w)
		(5) [Android - HttpURLConnection 基本教學 取得網頁資料'HTML, XML, JSON'](http://wannadoitnow.blogspot.tw/2015/10/android-httpurlconnection-httprequest.html)
		(6)[Android高效入門－讀取JSON資料與okHttp](http://litotom.com/2016/05/15/android-json-okhttp/)
4. [Android 6 變形金剛 - Android6CookbookPowenko](https://github.com/powenko/Android6CookbookPowenko) - Ch12 : TutorialHTTPGet




### 1. 預計會用到的 API
* 資料格式：JSON、XML
	* 在這裡以JSON格式為主

(1) [Google Maps Distance Matrix API](https://developers.google.com/maps/documentation/distance-matrix/?hl=zh-tw)
	* 用途：針對多個目的地和交通方式計算距離和所需時間。


(2) [Google Maps Directions API](https://developers.google.com/maps/documentation/directions/?hl=zh-tw)
	* 用途：傳回一連串途經地點的多部分路線規劃。提供多種交通方式的路線。


### 2. 試著將在 Google API 網頁上顯示的 JSON 資料回傳到 APP 上面。
* 本次使用 Google Maps Distance Matrix API
	* 出發點：Taipei
	* 目的地：Kaohsiung
	* API Key：AIzaSyBNQORdka1a2XM7TLCwDvR8BSd7DMlntdo
	* 網址：https://maps.googleapis.com/maps/api/distancematrix/json?origins=Taipei&destinations=Kaohsiung&key=AIzaSyBNQORdka1a2XM7TLCwDvR8BSd7DMlntdo

(1) 利用 APP 傳送要求，將網頁上的 JSON 資料回傳至 APP 畫面上


[預覽畫面]
![](/20160810/assets/1.png)




## [額外課題] 收集並記錄台電的OpenData資料，這些資料將用來模擬單位區域內可以支援多少台電動車同時充電。
* 資料來源：[政府資料開放平臺 - 台灣電力股份有限公司](http://data.gov.tw/taxonomy/term/465)


