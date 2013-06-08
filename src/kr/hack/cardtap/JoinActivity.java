package kr.hack.cardtap;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity {
	public static String DOMAIN;
	private static final boolean DEVELOPER_MODE = true;
	EditText email;
	EditText name;
	EditText pw;
	boolean status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (DEVELOPER_MODE) {
			StrictMode.enableDefaults();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);
		DOMAIN = getResources().getString(R.string.domain);

		Button button = (Button) findViewById(R.id.join_button);
		email = (EditText) findViewById(R.id.email);
		name = (EditText) findViewById(R.id.name);
		pw = (EditText) findViewById(R.id.password);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validateFormFormat()) {
					execute();
					if (status) {
						Intent intent = new Intent(getApplicationContext(),
								TabHolder.class);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), "중복된 아이디가 있습니다.",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		// for test
		email.setText("test");
		pw.setText("passtext");

	}

	protected boolean validateFormFormat() {
		if (!Util.isValidEmail(email.getText())) {
			Toast.makeText(this, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private void execute() {
		String json = readData();
		try {
			JSONObject jsonArray = new JSONObject(json);
			status = jsonArray.getBoolean("success");

			if (status) {
				// successful join
				Log.i("join", "join:" + status);
			} else {
				// error
				Log.i("join", "fail:" + status);
				finish();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String readData() {
		String url = DOMAIN + "/member/join.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email.getText().toString()));
		params.add(new BasicNameValuePair("name", name.getText().toString()));
		params.add(new BasicNameValuePair("password", pw.getText().toString()));

		return Util.getJSONString(url, params);
	}

}
