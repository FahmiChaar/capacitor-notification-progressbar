package tn.converto.plugins.notification.download.progress;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import android.support.v7.app.NotificationCompat;
import android.app.NotificationManager;

@NativePlugin
public class NotificationDownloadProgress extends Plugin {

    private Integer notificationId = 203
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }
    
    @PluginMethod
    public void create(PluginCall call) {
        try {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //Set notification information:
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
            notificationBuilder.setOngoing(true)
                            .setContentTitle("Notification Content Title")
                            .setContentText("Notification Content Text")
                            .setProgress(100, 0, false);
            //Send the notification:
            Notification notification = notificationBuilder.build();
            notificationManager.notify(notificationID, notification);
            JSObject ret = new JSObject();
            ret.put("message", 'Notification successfully created');
            call.success(ret);
        }catch(e) {
            call.reject("Error when create notification");
        }
    }

    @PluginMethod
    public void setProgress(PluginCall call) {
        JSObject params = call.getData();
        //Update notification information:
        float progress = params.getDouble("progress");
        notificationBuilder.setProgress(100, progress, false);

        //Send the notification:
        notification = notificationBuilder.build();
        notificationManager.notify(notificationID, notification);
    }
}
