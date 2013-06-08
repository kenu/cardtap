package kr.hack.cardtap;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CardShareActivity extends Activity {
	public static String DOMAIN;

	EditText to_email;
	EditText message;
	boolean status;

	private int sid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardshare);
		DOMAIN = getResources().getString(R.string.domain);

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
		// for test
		to_email.setText("jun318zz@gmail.com");
		message.setText("Hello, It's Me!");
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
