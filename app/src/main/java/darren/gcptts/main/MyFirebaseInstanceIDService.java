package darren.gcptts.main;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "mFirebaseIIDService";
    private static final String SUBSCRIBE_TO = global.langu;


    public void onTokenRefresh() {
        /*
          This method is invoked whenever the token refreshes
          OPTIONAL: If you want to send messages to this application instance
          or manage this apps subscriptions on the server side,
          you can send this token to your server.
        */
        Task<String> token = FirebaseMessaging.getInstance().getToken();

        // Once the token is generated, subscribe to topic with the userId
        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
        Log.i(TAG, "onTokenRefresh completed with token: " + token);
    }
}