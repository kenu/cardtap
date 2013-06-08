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
				if (Util.validateFormFormat(getApplicationContext(), email)) {
					execute();
				}
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
			Toast.makeText(this, "Thank you for Join!!!", Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(this, TabHolder.class);
			startActivity(intent);
			finish();

		} else {
			// error
			Toast.makeText(this, "Duplicated Email Exists!\nTry Other Email, Please",
					Toast.LENGTH_LONG).show();
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
