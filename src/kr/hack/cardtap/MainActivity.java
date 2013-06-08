package kr.hack.cardtap;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (DEVELOPER_MODE) {
			StrictMode.enableDefaults();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DOMAIN = getResources().getString(R.string.domain);

		Button button = (Button) findViewById(R.id.button1);
		Button joinButton = (Button) findViewById(R.id.join_button);
		email = (EditText) findViewById(R.id.id);
		pw = (EditText) findViewById(R.id.password);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateFormFormat()) {
					Intent intent = new Intent(getApplicationContext(),
							TabHolder.class);
					startActivity(intent);
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
		
        //for test
        email.setText("a@a.com");
        pw.setText("aa");
        
	}

	protected boolean validateFormFormat() {
		if (!Util.isValidEmail(email.getText())) {
			Toast.makeText(this, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	public String readData() {
		String url = DOMAIN + "/member/login.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email.getText().toString()));
		params.add(new BasicNameValuePair("password", pw.getText().toString()));

		return Util.getJSONString(url, params);
	}

}
