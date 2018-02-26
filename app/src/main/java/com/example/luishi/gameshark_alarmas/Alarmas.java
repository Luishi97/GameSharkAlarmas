package com.example.luishi.gameshark_alarmas;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.example.luishi.gameshark_alarmas.R.drawable.ic_launcher_background;

/**
 * Created by Luishi on 10/2/2018.
 */

public class Alarmas extends AppCompatActivity
{
    private String nomArch = "auxi.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmas);
        generarBotones();
    }

    public void generarBotones()
    {
        for(int i=1; i <= 10; i++)
            generarBotones(i);

        tvsAdicionales();
    }

    private void tvsAdicionales() {
        if (leerNotificacion() != "") {
            String[] teles = leerNotificacion().split("'");

        if (teles.length > 0)
            for (String i : teles) {
                LinearLayout sc = findViewById(R.id.scroll);
                LinearLayout ll = new LinearLayout(this);
                LinearLayout lado = new LinearLayout(this);

                ImageView iv = new ImageView(this);
                Button btn = new Button(this);
                Button btnEli = new Button(this);

                ll.setOrientation(LinearLayout.VERTICAL);

                if (Integer.parseInt(i) <= 2)
                    iv.setImageResource(R.mipmap.tv_antigua);
                else
                    iv.setImageResource(R.mipmap.tv_nueva);

                btn.setText("Tele " + i + "*");

                btn.setId(Integer.parseInt(i));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        accionBotonExtra(v);
                    }
                });

                btnEli.setId(Integer.parseInt(2 + i));
                btnEli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eliminar(v);
                    }
                });

                ll.addView(iv);

                lado.addView(btn);
                lado.addView(btnEli);

                ll.addView(lado);

                sc.addView(ll);
            }
        }
    }

    public void eliminar(View v)
    {
        String texto = leerNotificacion();
        String txt = "";
        String []telesExtras = texto.split("'");
        int cod = (v.getId())%10;
        for(String i : telesExtras)
            if (Integer.parseInt(i) == cod)
            {
                txt = eli(telesExtras,cod);
                guardar(txt);
                generarBotones();
                break;
            }

    }

    public void guardar(String txt)
    {
        try
        {
            OutputStreamWriter fout=
                    new OutputStreamWriter(
                            openFileOutput(nomArch, Context.MODE_PRIVATE));

            fout.write(txt);
            fout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String eli(String[] telesExtras, int cod)
    {
        String [] v = new String[telesExtras.length];
        String txt="";
        int j=0;
        for(String i : telesExtras)
        {
            if(Integer.parseInt(i)!=cod)
                v[j] = i;
            j++;
        }
        for(String i: v)
        {
            txt += ("2"+i+"'");
        }
        return txt;
    }

    public void accionBotonExtra(View v)
    {
        switch (v.getId())
        {
            case 1:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "11")
                        .putExtra("esExtra", "si"));
                break;
            case 2:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "12")
                        .putExtra("esExtra", "si"));
                break;
            case 3:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "13")
                        .putExtra("esExtra", "si"));
                break;
            case 4:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "14")
                        .putExtra("esExtra", "si"));
                break;
            case 5:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "15"));
                break;
            case 6:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "16")
                        .putExtra("esExtra", "si"));
                break;
            case 7:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "17")
                        .putExtra("esExtra", "si"));
                break;
            case 8:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "18")
                        .putExtra("esExtra", "si"));
                break;
            case 9:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "19")
                        .putExtra("esExtra", "si"));
                break;
            case 10:
                startActivity(new Intent(this, setAlarmas.class)
                        .putExtra("tv", "20")
                        .putExtra("esExtra", "si"));

                break;
        }
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }

    public String leerNotificacion()
    {
        try
        {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    openFileInput(nomArch)));

            String texto = fin.readLine();
            fin.close();
            return texto;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
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
