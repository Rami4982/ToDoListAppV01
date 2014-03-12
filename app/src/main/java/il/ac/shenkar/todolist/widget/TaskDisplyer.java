package il.ac.shenkar.todolist.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ListView;
import android.widget.RemoteViews;

import javax.inject.Inject;

import il.ac.shenkar.todolist.R;
import il.ac.shenkar.todolist.controllers.taskList.TaskListActivity;
import roboguice.inject.InjectView;

/**
 * Implementation of App Widget functionality.
 */
public class TaskDisplyer extends AppWidgetProvider
{
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {
/*        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.task_displyer);
        Intent intent = new Intent(context, TaskListService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(appWidgetId, R.id.taskList, intent);
        views.setEmptyView(R.id.taskList, R.id.empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);*/

        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),R.layout.task_displyer);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, TaskListService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.taskList,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.taskList, R.id.empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}


