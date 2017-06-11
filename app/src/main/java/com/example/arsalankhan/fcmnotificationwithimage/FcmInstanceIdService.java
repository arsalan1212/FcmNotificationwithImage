package com.example.arsalankhan.fcmnotificationwithimage;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Arsalan khan on 6/11/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String Token= FirebaseInstanceId.getInstance().getToken();

        Log.d("Token:",Token);
    }
}
