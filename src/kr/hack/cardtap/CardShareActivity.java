package kr.hack.cardtap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CardShareActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardshare);

		Button shareButton = (Button) findViewById(R.id.share_button);

		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 메일 보내고,
				// 발송 결과 표시
			}
		});
	}

}
