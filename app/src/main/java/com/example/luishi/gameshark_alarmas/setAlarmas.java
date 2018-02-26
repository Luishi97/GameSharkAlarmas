package com.example.luishi.gameshark_alarmas;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.support.annotation.IntegerRes;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionProvider;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Calendar;

import static android.app.Notification.VISIBILITY_PUBLIC;

/**
 * Created by Luishi on 10/2/2018.
 */

public class setAlarmas extends AppCompatActivity
{
    private String tv;
    private Spinner sp;
    private EditText ent;
    private EditText sal;
    private EditText deuda;
    private TextView total;
    private double extra = 0;
    private int nroControles;
    private String nomArch = "prueba11";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarmas);

        tv = getIntent().getExtras().getString("tv");
        TextView tv = findViewById(R.id.titulo);
        tv.setText("Tele "+this.tv);

        nomArch += (this.tv+".txt");

        if (this.tv.equals("10"))
            esExtra();

        ent = findViewById(R.id.Entrada);
        sal = findViewById(R.id.Salida);

        deuda = findViewById(R.id.extra);
        //deuda.setText("0");
        deuda.addTextChangedListener(new TextWatcher()
        {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                calcularTotal();
            }
            @Override public void afterTextChanged(Editable editable){}
        });

        total = findViewById(R.id.total);

        sp = findViewById(R.id.nroControles);
        String [] num = {"1", "2", "3", "4", "5", "6"};
        sp.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, num));
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                nroControles = Integer.valueOf(adapterView.getItemAtPosition(pos).toString());
                calcularTotal();
            }

            @Override public void onNothingSelected(AdapterView<?> parent){}
        });
        String datos = leerNotificacion();
        if(datos != "") {
            String[] dat = datos.split("'");
            ponerDatosEnLosText(dat);
        }
        calcularTotal();
    }

    private void ponerDatosEnLosText(String[] datos)
    {
        switch (datos.length)
        {
            case 1:
                if(!datos[0].equals("1"))
                    sp.setSelection(Integer.parseInt(datos[0]));
                break;
            case 2:
                if(datos[0].contains(":"))
                    ent.setText(datos[0]);
                sp.setSelection(Integer.parseInt(datos[1]));
                break;
            case 3:
                ent.setText(datos[0]);
                if(!datos[1].equals("0"))
                {
                    deuda.setText(datos[1]);
                    sp.setSelection(Integer.parseInt(datos[2]));
                }else if(datos[1].contains(":"))
                {
                    sal.setText(datos[1]);
                    sp.setSelection(Integer.parseInt(datos[2]));
                }else
                {
                    deuda.setText(datos[1]);
                    sp.setSelection(Integer.parseInt(datos[2]));
                }
                break;
            case 4:
                ent.setText(datos[0]);
                sal.setText(datos[1]);
                deuda.setText(datos[2]);
                sp.setSelection(Integer.parseInt(datos[3]));
        }
    }

    public void ponerNotificacion(View v)
    {
        String textoDeArch = "";
        textoDeArch += (String.valueOf(ent.getText().toString() == "" ? "" : ent.getText().toString()+"'"));
        textoDeArch += (String.valueOf(sal.getText().toString() == "" ? "" : sal.getText().toString()+"'"));
        textoDeArch += (String.valueOf(deuda.getText())=="" ? "" : deuda.getText().toString())+"'";
        textoDeArch += String.valueOf(sp.getSelectedItemPosition());
        try
        {
            OutputStreamWriter fout=
                    new OutputStreamWriter(
                            openFileOutput(nomArch, Context.MODE_PRIVATE));

            fout.write(textoDeArch);
            fout.close();
            alarma();
            leerNotificacion();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void alarma()
    {
        //if(sal.getText().toString().equals(""))
        //{
            Intent i = new Intent(this, MyBroadcastReceiver.class);
            i.putExtra("tele", this.tv);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sal.getText().toString().split(":")[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(sal.getText().toString().split(":")[1]));

            PendingIntent pi = PendingIntent.getBroadcast(this.getApplicationContext(), Integer.parseInt(this.tv), i, Integer.parseInt(this.tv));
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 800, pi);
            Toast.makeText(this, "Alarma puesta", Toast.LENGTH_SHORT).show();
        //}
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
            Toast.makeText(this,
                    texto, Toast.LENGTH_SHORT).show();
            return texto;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void esExtra()
    {
        LinearLayout tr = findViewById(R.id.maquina);
        tr.removeAllViews();

        Spinner sp = new Spinner(this);
        sp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        String [] tvs = {"Tele 1", "Tele 2", "Tele 3", "Tele 4", "Tele 5", "Tele 6", "Tele 7", "Tele 8", "Tele 9"};
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tvs));
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
               cambiaso(adapterView,view,pos,id);
            }
            @Override public void onNothingSelected(AdapterView<?> adapterView){}});
        tr.addView(sp);
    }
    public void cambiaso(AdapterView<?> adapterView, View view, int pos, long id)
    {
        Toast.makeText(adapterView.getContext(),
                (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
        this.tv = adapterView.getItemAtPosition(pos).toString().split(" ")[1];
        this.nomArch = "aux"+this.tv+"txt";
        calcularTotal();
    }

    public void calcularTotal()
    {
        int dif = obtenerMinutos(sal)-obtenerMinutos(ent);
        double hora = dif < 0 ? 0 : (dif*precio());
        hora *= nroControles;

        String d = deuda.getText().toString();
        if(d.equals("")||d.equals(null))
            d = "0";
        else if(d.contains(","))
            d = d.replace(',', '.');
        hora += Double.parseDouble(d);
        total.setText(String.valueOf(hora));
    }

    private double precio()
    {
        switch (Integer.parseInt(this.tv))
        {
            case 1:
                return 0.05;
            case 2:
                return 0.05;
            case 3:
                return 0.05;
            case 4:
                return 0.083;
            case 5:
                return 0.083;
            case 6:
                return 0.083;
            case 7:
                return 0.083;
            case 8:
                return 0.11667;
            case 9:
                return 0.11667;
        }
        return 0;
    }

    public int obtenerMinutos(EditText edt)
    {
        if(edt.getText().toString().equals(""))
            return 0;
        String [] hora = edt.getText().toString().split(":");
        int h = Integer.parseInt(hora[0])*60;
        int m = Integer.parseInt(hora[1]);
        return h+m;
    }

    public void obtenerHoraSalida(View v)
    {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m= c.get(Calendar.MINUTE);

        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int horaDelDia, int minutos)
            {
                String hora = (horaDelDia < 10) ? String.valueOf("0"+horaDelDia) : String.valueOf(horaDelDia);
                String min = (minutos < 10) ? String.valueOf("0"+minutos) : String.valueOf(minutos);
                sal.setText(hora+":"+min);
                calcularTotal();
            }
        },h , m, true);
        recogerHora.show();
    }

    public void obtenerHoraEntrada(View v)
    {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m= c.get(Calendar.MINUTE);

        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int horaDelDia, int minutos)
            {
                String hora = (horaDelDia < 10) ? String.valueOf("0"+horaDelDia) : String.valueOf(horaDelDia);
                String min = (minutos < 10) ? String.valueOf("0"+minutos) : String.valueOf(minutos);
                ent.setText(hora+":"+min);
            }
        },h , m, true);
        recogerHora.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            startActivity(new Intent(this, Alarmas.class));
            overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ponerCero(View view)
    {
        EditText e = findViewById(R.id.Salida);
        e.setText("");
    }

}
