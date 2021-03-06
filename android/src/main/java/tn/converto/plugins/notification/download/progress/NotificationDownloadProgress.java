package tn.converto.plugins.notification.download.progress;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import android.app.NotificationChannel;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import tn.converto.plugins.notification.download.progress.capacitorpluginnotificationdownloadprogress.R;


@NativePlugin
public class NotificationDownloadProgress extends Plugin {


    private static final String TAG = "CNDProgress";

    private NotificationManager notificationManager;
    private NotificationManagerCompat notificationManagerCompat;
    private NotificationCompat.Builder notificationCompatBuilder;
    private int notificationId = 405;
    private String CHANNEL_ID = "capacitor-notification-download-progress-channel-id";
    private Boolean destroyOnDone = false;

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // The user-visible name of the channel.
            CharSequence name = "capacitor-plugin-notification-download-progress";
            // The user-visible description of the channel.
            String description = "capacitor-plugin-download-progress notification";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
    }

    @PluginMethod
    public void create(PluginCall call) {
        try {

            String contentTitle = call.getString("contentTitle");
            String contentText = call.getString("contentText");
            destroyOnDone = call.getBoolean("destroyOnDone");
            if (contentTitle == null) {
                contentTitle = "Download Start";
            }
            if (contentText == null) {
                contentText = "Download progress";
            }

            Context context = getContext();
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            this.createNotificationChannel();

            notificationCompatBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setProgress(100, 0, false)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManagerCompat = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            notificationManagerCompat.notify(notificationId, notificationCompatBuilder.build());

            JSObject ret = new JSObject();
            ret.put("message", "Notification successfully created");
            call.success(ret);
        }catch(Exception e) {
            Log.i(TAG,"Error create notification");
            Log.i(TAG, e.getMessage());
            e.printStackTrace();
            call.reject("Error when create notification");
        }
    }

    @PluginMethod
    public void updateProgress(PluginCall call) {
        try {
            Context context = getContext();
            //Update notification information:
            int progress = call.getInt("progress");
            notificationCompatBuilder.setProgress(100, progress, false)
                                    .setContentText(progress+"%");

            //Send the notification:
            notificationManagerCompat = notificationManagerCompat.from(context);
            notificationManagerCompat.notify(notificationId, notificationCompatBuilder.build());
            if (progress >= 100) {
                JSObject ret = new JSObject();
                ret.put("message", "Download complete");

                String doneTitle = call.getString("doneTitle");
                String doneText = call.getString("doneText");
                if (doneTitle == null) {
                    doneTitle = "Download complete";
                }
                if (doneText == null) {
                    doneText = "100%";
                }
                if (destroyOnDone == null || destroyOnDone == false) {
                    notificationCompatBuilder.setContentTitle(doneTitle)
                            .setContentText(doneText)
                            .setProgress(0,0,false);
                    notificationManagerCompat.notify(notificationId, notificationCompatBuilder.build());
                } else {
                    notificationManagerCompat.cancel(notificationId);
                }
                call.success(ret);
            }
        }catch(Exception e) {
            Log.i(TAG,"Error update progress");
            Log.i(TAG, e.getMessage());
            e.printStackTrace();
            call.reject("Error when update progress bar " + e.getMessage());
        }
    }

    @PluginMethod
    public void destroy(PluginCall call) {
        try {
            notificationManagerCompat.cancel(notificationId);
            JSObject ret = new JSObject();
            ret.put("message", "Notification successfully destroyed");
            call.success(ret);
        }catch(Exception e) {
            Log.i(TAG,"Error destroy notification");
            Log.i(TAG, e.getMessage());
            e.printStackTrace();
            call.reject("Error when destroy notification");
        }
    }
}
