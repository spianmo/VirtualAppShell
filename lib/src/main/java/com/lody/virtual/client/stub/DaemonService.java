package com.lody.virtual.client.stub;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.lody.virtual.R;
import android.support.v4.app.NotificationCompat;

/**
 * @author Lody
 *
 */
public class DaemonService extends Service {

    private static final int NOTIFY_ID = 1;

	public static void startup(Context context) {
		context.startService(new Intent(context, DaemonService.class));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		startup(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
        startService(new Intent(this, InnerService.class));

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.mipmap.ic_launcher_s);
		builder.setContentTitle("VXP is running");
		builder.setContentText("hallo");
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder.setChannelId("notification_id");
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			NotificationChannel channel = new NotificationChannel("notification_id", "notification_name", NotificationManager.IMPORTANCE_LOW);
			notificationManager.createNotificationChannel(channel);
		}
        startForeground(NOTIFY_ID, builder.build());

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	public static final class InnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			builder.setSmallIcon(R.mipmap.ic_launcher_s);
			builder.setContentTitle("VXP is running");
			builder.setContentText("hallo");
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				builder.setChannelId("notification_id");
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
				NotificationChannel channel = new NotificationChannel("notification_id", "notification_name", NotificationManager.IMPORTANCE_LOW);
				notificationManager.createNotificationChannel(channel);
			}
			startForeground(NOTIFY_ID, builder.build());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
	}


}
