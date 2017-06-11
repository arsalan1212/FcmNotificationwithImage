package com.example.arsalankhan.fcmnotificationwithimage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Arsalan khan on 6/11/2017.
 */

public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title,message,image_url;

        if(remoteMessage.getData().size()>0){
            // there is something in the data payload

            title=remoteMessage.getData().get("title");
            message=remoteMessage.getData().get("message");
            image_url=remoteMessage.getData().get("image_url");

            Intent intent=new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            final NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
            builder.setContentTitle(title);
            builder.setContentText(message);

            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            //setting the Notification sound
            Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(soundUri);

            builder.setSmallIcon(R.mipmap.ic_launcher);

            //setting the Volley imageRequest queue to download image

            ImageRequest imageRequest=new ImageRequest(image_url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {

                    Toast.makeText(FcmMessagingService.this, "Response: "+response, Toast.LENGTH_SHORT).show();
                    //setting image to the Notification
                    builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));

                    NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0,builder.build());

                }
            }, 0, 0,null, Bitmap.Config.RGB_565,new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Error",error.toString());
                }
            });

            RequestQueue requestQueue=Volley.newRequestQueue(this);
            requestQueue.add(imageRequest);
        }
    }
}
