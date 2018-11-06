package id.go.muaraenimkab.mance.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;


/**
 * Created by Tris on 4/5/2018.
 */
class MyNotificationManager {

    private static final int ID_BIG_NOTIFICATION = 234;

    private static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;

    private NotificationManager notifManager;

    MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void showSmallNotification(String title, String message, Long time, String flag, String id) {
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
//        notification = mBuilder.setSmallIcon(R.drawable.mance).setTicker("Muara Enim Center").setWhen(0)
//                .setAutoCancel(true)
//                .setContentIntent(resultPendingIntent)
//                .setContentTitle("Muara Enim Center")
//                .setWhen(time)
//                .setSmallIcon(R.drawable.mance)
//                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.mance))
//                .setContentText("Muara Enim Center")
//                .setSound(defaultSoundUri)
//                .build();
//
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
//        Objects.requireNonNull(notificationManager).notify(ID_SMALL_NOTIFICATION, notification);

        createNotificationSmall(mCtx, title, message, time, flag, id);
    }

    //the method will show a big notification with an imageerror
    //parameters are title for message title, message for message text, url of the big imageerror and an intent that will open
    //when you will tap on the notification
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void showBigNotification(String title, String message, String url, String flag, String id) {
//        PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        mCtx,
//                        ID_BIG_NOTIFICATION,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
//        bigPictureStyle.setBigContentTitle(title);
//        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
//        bigPictureStyle.bigPicture(getBitmapFromURL(url));
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
//        Notification notification;
//        notification = mBuilder.setSmallIcon(R.drawable.mance).setTicker(title).setWhen(0)
//                .setAutoCancel(true)
//                .setContentIntent(resultPendingIntent)
//                .setContentTitle(title)
//                .setStyle(bigPictureStyle)
//                .setSmallIcon(R.drawable.mance)
//                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.mance))
//                .setContentText(message)
//                .build();
//
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(ID_BIG_NOTIFICATION, notification);
        createNotificationbig(mCtx, title, message, url, flag, id);
    }

    //The method will return Bitmap from an imageerror URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createNotificationSmall(Context context, String title, String message, Long time, String flag, String idberita) {
        final int NOTIFY_ID = 0;
        String id = "1";
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("flag", flag);
        intent.putExtra("id", idberita);

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = Objects.requireNonNull(notifManager).getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);

            pendingIntent = PendingIntent.getActivity(
                    context,
                    ID_SMALL_NOTIFICATION,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle(title)
                    .setSmallIcon(R.drawable.logomance)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setWhen(time)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.logomance))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        } else {
            builder = new NotificationCompat.Builder(context, id);

            pendingIntent = PendingIntent.getActivity(
                    context,
                    ID_SMALL_NOTIFICATION,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            builder.setContentTitle(title)
                    .setSmallIcon(R.drawable.logomance)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setWhen(time)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createNotificationbig(Context context, String title, String message, String url, String flag, String idberita) {
        final int NOTIFY_ID = 0;
        String id = "1";
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("flag", flag);
        intent.putExtra("id", idberita);

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = Objects.requireNonNull(notifManager).getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);

            pendingIntent = PendingIntent.getActivity(
                    context,
                    ID_BIG_NOTIFICATION,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle(title)
                    .setSmallIcon(R.drawable.logomance)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setStyle(bigPictureStyle)
                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.logomance))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(context, id);

            pendingIntent = PendingIntent.getActivity(
                    context,
                    ID_BIG_NOTIFICATION,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle(title)
                    .setSmallIcon(R.drawable.logomance)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(message)
                    .setStyle(bigPictureStyle)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
