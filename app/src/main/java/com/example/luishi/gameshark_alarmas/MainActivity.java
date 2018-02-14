package com.example.luishi.gameshark_alarmas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void entrar(View view)
    {
        startActivity(new Intent(this, Alarmas.class));
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }
}
