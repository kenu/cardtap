package kr.hack.cardtap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		Button logoutButton = (Button) findViewById(R.id.logout_button);
		logoutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Store.sid = 0;
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}

}
