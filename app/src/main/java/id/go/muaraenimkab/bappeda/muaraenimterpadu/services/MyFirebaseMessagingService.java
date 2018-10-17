package id.go.muaraenimkab.bappeda.muaraenimterpadu.services;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tris on 4/5/2018.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    public static String title, message, imageurl,flag,id;
    MyNotificationManager mNotificationManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");
            Long tsLong = System.currentTimeMillis();

            //parsing json data
            title = data.getString("title");
            message = data.getString("message");
            imageurl = data.getString("imageurl");
            flag = data.getString("flag");
            id = data.getString("id");
//            Log.e("image", imageurl);

            //creating MyNotificationManager object
            mNotificationManager = new MyNotificationManager(getApplicationContext());
            if (imageurl.isEmpty()) {
                mNotificationManager.showSmallNotification(title, message, tsLong,flag,id);
            } else{
                mNotificationManager.showBigNotification(title, message, imageurl,flag,id);
            }

            //creating an intent for the notification --> bottom

//            if there is no imageerror
            //if(imageUrl.equals("null")){
            //              displaying small notification
//            mNotificationManager.showSmallNotification(title, message, intent);
            //}
            //else{
            //if there is an imageerror
            //displaying a big notification
            //mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            //}
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}
