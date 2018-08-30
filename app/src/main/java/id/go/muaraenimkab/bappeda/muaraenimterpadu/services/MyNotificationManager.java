package id.go.muaraenimkab.bappeda.muaraenimterpadu.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;


/**
 * Created by Tris on 4/5/2018.
 */
public class MyNotificationManager {

    //public static final int ID_BIG_NOTIFICATION = 234;

//    private static final int ID_SMALL_NOTIFICATION = 235;
//
//    private Context mCtx;
//
//    MyNotificationManager(Context mCtx) {
//        this.mCtx = mCtx;
//    }
//
//    //the method will show a small notification
//    //parameters are title for message title, message for message text and an intent that will open
//    //when you will tap on the notification
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void showSmallNotification(String title, String message, Long time, Intent intent) {
//        PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        mCtx,
//                        ID_SMALL_NOTIFICATION,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
//        Notification notification;
//        notification = mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground).setTicker("Muara Enim Terpadu").setWhen(0)
//                .setAutoCancel(true)
//                .setContentIntent(resultPendingIntent)
//                .setContentTitle("Muara Enim Terpadu")
//                .setWhen(time)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.ic_launcher_foreground))
//                .setContentText("Muara Enim Terpadu")
//                .setSound(defaultSoundUri)
//                .build();
//
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
//        Objects.requireNonNull(notificationManager).notify(ID_SMALL_NOTIFICATION, notification);
//    }
}
