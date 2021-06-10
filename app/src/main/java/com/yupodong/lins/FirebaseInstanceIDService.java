package com.yupodong.lins;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

        private static final String TAG = "MyFirebaseIIDService";

        @Override
        public void onTokenRefresh() {
            // Get updated InstanceID token.
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);

            // If you want to send messages to this application instance or
            // manage this apps subscriptions on the server side, send the
            // Instance ID token to your app server.
            sendRegistrationToServer(refreshedToken);
        }

        private void sendRegistrationToServer(String token){
            //기타 작업으로 활용

        }
//        // [START refresh_token]
//        @Override
//        public void onTokenRefresh() {
//            // Get updated InstanceID token.
//            String token = FirebaseInstanceId.getInstance().getToken();
//            //여기서부터 새로 추가된 코드
//            Log.w(TAG,"Refreshed token: "+token);
//            sendRegistrationToServer(token);
//        }
//        private void sendRegistrationToServer(String token) {
//            // TODO: Implement this method to send token to your app server.
//        }

}




//package com.yupodong.lins;
//        import android.app.Notification;
//        import android.app.NotificationChannel;
//        import android.app.NotificationManager;
//        import android.app.PendingIntent;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.graphics.Bitmap;
//        import android.graphics.BitmapFactory;
//        import android.graphics.Color;
//        import android.media.RingtoneManager;
//        import android.net.Uri;
//        import android.os.Build;
//        import android.os.PowerManager;
//        import android.util.Log;
//        import androidx.core.app.NotificationCompat;
//        import com.google.firebase.messaging.RemoteMessage;
//        import com.firebase.jobdispatcher.FirebaseJobDispatcher;
//        import com.firebase.jobdispatcher.GooglePlayDriver;
//        import com.firebase.jobdispatcher.Job;
//
//public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
//
//    private static final String TAG = "MyFirebaseMsgService";
//
//    //푸시 알림 설정
//    private String title ="새로운 일정";
//    private String body ="새로운 일정을 확인해보세요";
//    private String color ="";
//
//
//    // [START receive_message]
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // 푸시알림 메시지 분기
//        //putDate를 사용했을때 data 가져오기
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            title = remoteMessage.getData().get("title");
//            body = remoteMessage.getData().get("body");
//            color = remoteMessage.getData().get("color");
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//
//        }
//
//        //Notification 사용했을때 data 가져오기
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getColor());
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getIcon());
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//        sendNotification();
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
//
//    }
//    // [END receive_message]
//
//
//    // [START on_new_token]
//
//    @Override
//    public void onNewToken(String token) {
//        Log.d(TAG, "Refreshed token: " + token);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
//    }
//    // [END on_new_token]
//
//    private void scheduleJob() {
//        // [START dispatch_job]
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
//        // [END dispatch_job]
//    }
//
//    private void handleNow() {
//        Log.d(TAG, "Short lived task is done.");
//    }
//    private void sendRegistrationToServer(String token) {
//        // TODO: Implement this method to send token to your app server.
//    }
//
//    private void sendNotification() {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = "채널 ID";
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.drawable.ic_star)
//                        .setContentTitle(title)
//                        .setContentText(body)
//                        .setColor(Color.parseColor(color))
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent)
//                        .setPriority(Notification.PRIORITY_HIGH);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
//
//}


