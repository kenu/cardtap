package kr.hack.cardtap;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static String DOMAIN;
	private static final boolean DEVELOPER_MODE = true;

	EditText email;
	EditText pw;
	boolean status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (DEVELOPER_MODE) {
			StrictMode.enableDefaults();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DOMAIN = getResources().getString(R.string.domain);

		Button loginButton = (Button) findViewById(R.id.button1);
		Button joinButton = (Button) findViewById(R.id.join_button);
		email = (EditText) findViewById(R.id.id);
		pw = (EditText) findViewById(R.id.password);

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Context context = getApplicationContext();

				if (Util.validateFormFormat(context, email)) {
					execute();
				}
			}
		});
		joinButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						JoinActivity.class);
				startActivity(intent);
			}

		});

		// for test
		email.setText("srom.moon@gmail.com");
		pw.setText("aa");

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
			Intent intent = new Intent(this, TabHolder.class);
			startActivity(intent);
		} else {
			// error
			Toast.makeText(this, "Fail to Login!!!", Toast.LENGTH_LONG).show();
		}
	}

	public String readData() {
		String url = DOMAIN + "/member/login.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email.getText().toString()));
		params.add(new BasicNameValuePair("password", pw.getText().toString()));

		return Util.getJSONString(url, params);
	}

}
