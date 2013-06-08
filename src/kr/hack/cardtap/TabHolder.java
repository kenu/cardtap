package kr.hack.cardtap;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHolder extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator(getResources().getText(R.string.my_card))
                .setContent(new Intent(this, MyCardActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(getResources().getText(R.string.card_share))
                .setContent(new Intent(this, CardShareActivity.class)));
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator(getResources().getText(R.string.profile))
                .setContent(new Intent(this, ProfileActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }
}