package com.example.luishi.gameshark_alarmas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Luishi on 18/2/2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver
{
    private String tv;
    private Context context;
    private NotificationCompat.Builder notif;
    private MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        tv = intent.getExtras().getString("tv");
        mp = MediaPlayer.create(context, R.raw.alrm);
        mp.start();
        notif = new NotificationCompat.Builder(context);
        this.context = context;
        notif.setAutoCancel(true);
        notificaion();
    }

    private void notificaion()
    {
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        notif.setSmallIcon(R.mipmap.ic_launcher);
        notif.setTicker("Nueva notificacion");
        notif.setPriority(Notification.PRIORITY_MAX);
        notif.setWhen(System.currentTimeMillis());
        notif.setContentTitle("Titulo");
        notif.setContentText("Tutorial auto notificaciones");
        notif.setPriority(Notification.PRIORITY_HIGH);
        notif.setVisibility(VISIBILITY_PUBLIC);
        notif.setLights(Color.BLUE, 1000, 5000);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notif.setSound(soundUri);

        Intent i = new Intent(context, setAlarmas.class);
        i.putExtra("tv",this.tv);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        notif.setContentIntent(pi);

        NotificationManager mn = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mn.notify(Integer.parseInt(this.tv), notif.build());
    }
}
