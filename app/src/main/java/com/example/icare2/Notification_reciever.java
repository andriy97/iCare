package com.example.icare2;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Notification_reciever extends BroadcastReceiver {
    List<Report> reports;
    @Override
    public void onReceive(Context context, Intent intent) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        reports = MainActivity.MyDatabase.myDao().getReportsDesc(); //tutti i report

        if(!reports.get(0).getData().equals(formattedDate)) { //se non c'è già il report quel giorno faccio partire la norifica

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            //intent per aprire mainActivity
            Intent activityDaAprire = new Intent(context, MainActivity.class);
            activityDaAprire.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, activityDaAprire, PendingIntent.FLAG_UPDATE_CURRENT);

            //delay notifica
                Intent intent2 = new Intent(context, PostPone_reciever.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 100, intent2, PendingIntent.FLAG_UPDATE_CURRENT);


            //creo canale
            String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

                // Configure the notification channel.
                notificationChannel.setDescription("Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }



            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.notification_drawer)
                    .setContentTitle("Non mettere a rischio la tua salute")
                    .setContentText("Clicca per aggiungere il report")
                    .setAutoCancel(true)
                    .addAction(R.mipmap.ic_launcher, "Ricorda più tardi",  pendingIntent2)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    //.build()
                    ;

            notificationManager.notify(100, builder.build());

        }

    }
}
