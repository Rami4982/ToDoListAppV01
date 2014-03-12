package il.ac.shenkar.todolist;

/**
 * Created by rami on 07/12/13.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import il.ac.shenkar.todolist.controllers.createTask.CreateTaskActivity;
import il.ac.shenkar.todolist.controllers.taskList.TaskListActivity;

public class MyBroadcastReceiver extends BroadcastReceiver
{
    public final static String EXTRA_MESSAGE = "il.ac.shenkar.todolist.MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String message = "";//intent.getStringExtra(CreateTaskActivity.EXTRA_MESSAGE);
        Intent myintent = new Intent(context, TaskListActivity.class);
        myintent.putExtra(EXTRA_MESSAGE, message);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myintent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent.FLAG_CANCEL_CURRENT |PendingIntent        // .FLAG_UPDATE_CURRENT
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, "Task To be Done", System.currentTimeMillis());
        notification.setLatestEventInfo(context, message, message, pendingIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;// hide the notification after its selected
        notificationManager.notify(0, notification);

    }
}
//Intent myintent = new Intent(context, NotificationReceiverActivity.class);
//  context.startActivity(intent);
//        Intent myintent = new Intent(context, TaskListActivity.class);
//Toast.makeText(context, "Don't panik but your time is up!!!!." + intent, Toast.LENGTH_LONG).show();