package com.example.icare2;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import static android.content.Context.ALARM_SERVICE;

public class PostPone_reciever extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)  context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(100); //tolgo la notifica dopo aver cliccato "ricorda piu tardi"

        Intent intent2 = new Intent(context, Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager= (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000*60*30, pendingIntent); //30 minuti di delay


    }
}