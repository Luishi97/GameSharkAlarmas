package com.example.luishi.gameshark_alarmas;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import static com.example.luishi.gameshark_alarmas.R.drawable.ic_launcher_background;

/**
 * Created by Luishi on 10/2/2018.
 */

public class Alarmas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmas);

        for(int i=1; i <= 10; i++)
            generarBotones(i);
    }

    private void generarBotones(int i)
    {
        LinearLayout sc = findViewById(R.id.scroll);
        LinearLayout ll = new LinearLayout(this);

        ImageView iv = new ImageView(this);
        Button btn = new Button(this);

        ll.setOrientation(LinearLayout.VERTICAL);

        if(i<=2)
            iv.setImageResource(R.mipmap.tv_antigua);
        else
            iv.setImageResource(R.mipmap.tv_nueva);

        if(i!=10)
            btn.setText("Tele "+i);
        else
            btn.setText("Tele Extra");

        btn.setId(i);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accionBoton(v);
            }
        });

        ll.addView(iv);
        ll.addView(btn);

        sc.addView(ll);
    }

    public void accionBoton(View v)
    {
        switch (v.getId())
        {
            case 1:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "1"));
                break;
            case 2:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "2"));
                break;
            case 3:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "3"));
                break;
            case 4:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "4"));
                break;
            case 5:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "5"));
                break;
            case 6:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "6"));
                break;
            case 7:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "7"));
                break;
            case 8:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "8"));
                break;
            case 9:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "9"));
                break;
            case 10:
                startActivity(new Intent(this, setAlarmas.class).putExtra("tv", "10"));
                break;
        }
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
