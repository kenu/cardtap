package kr.hack.cardtap;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MyCardActivity extends Activity {
	public static String DOMAIN;

	TextView like;
	TextView profile_name;
	TextView email;
	TextView web;
	TextView twitter;
	TextView facebook;
	
	boolean status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycard);
		DOMAIN = getResources().getString(R.string.domain);
		
		like = (TextView) findViewById(R.id.like);
		profile_name = (TextView) findViewById(R.id.profile_name);
		email = (TextView) findViewById(R.id.email);
		web = (TextView) findViewById(R.id.web);
		twitter = (TextView) findViewById(R.id.twitter);
		facebook = (TextView) findViewById(R.id.facebook);

		// load
		load(getApplicationContext());
		
	}

	private boolean load(Context applicationContext) {
		// sid 확인
		if (Store.sid == 0) {
			return false;
		}
		String url = DOMAIN + "/card/info.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sid", String.valueOf(Store.sid)));
		String json = Util.getJSONString(url, params);
		
		try {
			JSONObject info = new JSONObject(json);
			if (info != null) {
				like.setText(Util.checkNull(info.getString("like"), "0"));
				profile_name.setText(info.getString("name"));
				email.setText(info.getString("email"));
				web.setText(Util.checkNull(info.getString("web"), "    -"));
				twitter.setText(Util.checkNull(info.getString("twitter"), "    -"));
				facebook.setText(Util.checkNull(info.getString("facebook"), "    -"));
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return true;
	}

}
