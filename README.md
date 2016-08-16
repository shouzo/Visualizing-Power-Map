# Visualizing-Power-Map    視覺化電動機車能量地圖專題

## (一) 目前進度
一、製作單純的"位置資訊查詢 APP"。
- [x] [20160704 - 新增資料草稿](/20160704)
- [x] [20160711 - 新增測試用 Google Map App](/20160711/README.md)
- [x] [20160721 - 新增"位置資訊查詢 APP"(Demo)](/20160721/README.md)


二、將出發點到達目的地的路徑以 10 公尺為一個單位，分成N個座標並加以紀錄。

1. 將計算完的距離，以 10 公尺為單位，記錄該點的座標。
	* 取得的資料包含數值跟圖片資料，且還有不同路徑的規劃建議。

2. 收集並記錄台電的OpenData資料，這些資料將用來模擬單位區域內可以支援多少台電動車同時充電。
- [x] [20160810 - 將在 Google API 網頁上顯示的 JSON 資料回傳到 APP (Demo)](/20160810/README.md)




## (二) 前言
* 關於本專題的介紹：[視覺化電動機車運行能量地圖](https://shouzo.github.io/collections/data-science/20160617-MapProject-1.html#/)


## 分析單趟充放電能耗、電量
將里程數和當下的電量關係式用code寫出來。








## (三) 功能

### (1) 建立資料模型
* Google map 抓路徑
	* 從A點到B點(google地圖)，事先預測路況、轉換成縱面圖(有海拔差高度、路徑圖)
		* 全部紀錄(長條圖)
		* 例如：起始座標, 結束座標, 距離, 高度差
![](https://i.imgur.com/9TO9pQh.jpg)





### (2) 查表
![](https://i.imgur.com/XLcXnSV.jpg)



### (3) 最省電路徑
![](https://i.imgur.com/xmEG8nX.jpg)





## (四) 參考文獻
* 20160711 - [Android Tutorial 第四堂（2）設計地圖應用程式 - Google Maps Android API v2](http://www.codedata.com.tw/mobile/android-tutorial-the-4th-class-google-maps-android-api-v2/)
* 20160721 - [Android 6-5.x APP 開發教戰手冊](http://www.books.com.tw/products/0010693225) - CH10
* 20160810 - [Android 6 變形金剛 - Android6CookbookPowenko](https://github.com/powenko/Android6CookbookPowenko) - Ch12 : TutorialHTTPGet

