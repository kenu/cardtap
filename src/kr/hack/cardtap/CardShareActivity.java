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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CardShareActivity extends Activity {
	public static String DOMAIN;

	EditText to_email;
	EditText message;
	
	TextView like;
	TextView profile_name;
	TextView email;
	TextView web;
	TextView twitter;
	TextView facebook;
	
	boolean status;

	private int sid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardshare);
		DOMAIN = getResources().getString(R.string.domain);
		
		like = (TextView) findViewById(R.id.like);
		profile_name = (TextView) findViewById(R.id.profile_name);
		email = (TextView) findViewById(R.id.email);
		web = (TextView) findViewById(R.id.web);
		twitter = (TextView) findViewById(R.id.twitter);
		facebook = (TextView) findViewById(R.id.facebook);

		to_email = (EditText) findViewById(R.id.to_email);
		message = (EditText) findViewById(R.id.message);
		Button shareButton = (Button) findViewById(R.id.share_button);

		shareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 수신 메일 주소 확인
				if (Util.validateFormFormat(getApplicationContext(), to_email)) {
					// 메일 보내고, 발송 결과 표시
					execute();
				}
			}
		});

		// load
		load(getApplicationContext());
		
		// for test
		to_email.setText("jun318zz@gmail.com");
		message.setText("Hello, It's Me!");
		sid = Store.sid;
	}

	private boolean load(Context applicationContext) {
		// sid 확인
		if (Store.sid == 0) {
			return false;
		}
		String url = DOMAIN + "/card/shareform.php";
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

	private void execute() {
		String json = readData();
		try {
			JSONObject jsonArray = new JSONObject(json);
			status = jsonArray.getBoolean("success");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (status) {
			// successful
			Toast.makeText(this, "Share Message Sent!!!", Toast.LENGTH_SHORT)
					.show();
		} else {
			// error
			Toast.makeText(this, "Fail to Share Card!!! \nTry Later, Please",
					Toast.LENGTH_LONG).show();
		}
	}

	public String readData() {
		String url = DOMAIN + "/card/share.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sid", String.valueOf(sid)));
		params.add(new BasicNameValuePair("to_email", to_email.getText()
				.toString()));
		params.add(new BasicNameValuePair("message", message.getText()
				.toString()));

		return Util.getJSONString(url, params);
	}
}
