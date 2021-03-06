package com.itheima.gpsdemo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// 用到位置服务
	private LocationManager lm;
	private MyLocationListener listener;
	TextView tv_info;
	String information;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		tv_info=(TextView) findViewById(R.id.tv_info);
		
//		List<String> provider = lm.getAllProviders();
//		for(String l: provider){
//			System.out.println(l);
//		}
		listener = new MyLocationListener();
		//注册监听位置服务
		//给位置提供者设置条件
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

//设置参数细化：
//criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置为最大精度 
//criteria.setAltitudeRequired(false);//不要求海拔信息 
//criteria.setBearingRequired(false);//不要求方位信息 
//criteria.setCostAllowed(true);//是否允许付费 
//criteria.setPowerRequirement(Criteria.POWER_LOW);//对电量的要求 

		String proveder= lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(proveder, 0, 0, listener);
		
	}
	
	 public void click(View view)
	    {
	    	
	    	AsyncHttpClient client = new AsyncHttpClient();

	        String data = "userName="+information;
	        
	        client.get("http://10.0.2.2/MyProject/Show?" + data, new MyResponseHandler());
	    }
	 
	 class MyResponseHandler extends AsyncHttpResponseHandler {

			

			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, 
						"成功: statusCode: " + statusCode + ", body: " + new String(responseBody), 
						0).show();
				
			}

			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "失败: statusCode: " + statusCode, 0).show();
			}
	    }



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 取消监听位置服务
		lm.removeUpdates(listener);
		listener = null;

	}

	class MyLocationListener implements LocationListener {

		/**
		 * 当位置改变的时候回调
		 */

		@Override
		public void onLocationChanged(Location location) {
			String longitude = "longitude=" + location.getLongitude();
			String latitude = "latitude=" + location.getLatitude();
			String accuracy = "accuracy=" + location.getAccuracy();
			//TextView textview = new TextView(MainActivity.this);
			tv_info.setText(longitude + "\n" + latitude + "\n" + accuracy);
            information=longitude + latitude ;
			//setContentView(textview);

		}

		/**
		 * 当状态发生改变的时候回调 开启--关闭 ；关闭--开启
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		/**
		 * 某一个位置提供者可以使用了
		 */
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		/**
		 * 某一个位置提供者不可以使用了
		 */
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

}
