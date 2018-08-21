package com.ap.fmcgr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity{

    private Button btnLogin;
    private Button btnOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_activity);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnOffline = (Button) findViewById(R.id.btnOffline);

        btnLogin.setOnClickListener(mLogin);
        btnOffline.setOnClickListener(mOffline);

    }

    Button.OnClickListener mLogin = new Button.OnClickListener() {												//Metoda wywo ywana w skutek naci ni cia przycisku "Zr b"
        public void onClick(View v) {
            Intent myIntent = new Intent(WelcomeActivity.this, WebViewActivity.class);																							//Zeruje pola tekstowe aktywno ci odczytu
            startActivity(myIntent);
            finish();
        }
    };

    Button.OnClickListener mOffline = new Button.OnClickListener() {												//Metoda wywo ywana w skutek naci ni cia przycisku "Zr b"
        public void onClick(View v) {
            Intent myIntent = new Intent(WelcomeActivity.this, ReadsActivity.class);																							//Zeruje pola tekstowe aktywno ci odczytu
            startActivity(myIntent);
            //finish();
        }
    };

}
