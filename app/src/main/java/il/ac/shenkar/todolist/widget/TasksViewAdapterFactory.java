package il.ac.shenkar.todolist.widget;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import javax.inject.Inject;

import il.ac.shenkar.todolist.ItemDetails;
import il.ac.shenkar.todolist.R;
import il.ac.shenkar.todolist.startUp.DBConnectorProvider;
import il.ac.shenkar.todolist.startUp.DataBaseConnector;

public class TasksViewAdapterFactory implements RemoteViewsService.RemoteViewsFactory
{
    private final Context applicationContext;
    private final Intent intent;
    private final DataBaseConnector dataBaseConnector;
    private final List<ItemDetails> itemDetailsList;

    public TasksViewAdapterFactory(Context applicationContext, Intent intent)
    {
        this.applicationContext = applicationContext;
        this.intent = intent;
        this.dataBaseConnector = DBConnectorProvider.get(applicationContext);
        this.itemDetailsList =  dataBaseConnector.getItems();
    }

    @Override
    public void onCreate()
    {
    }

    @Override
    public void onDataSetChanged()
    {
    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public int getCount()
    {
        return itemDetailsList.size();
    }

    @Override
    public RemoteViews getViewAt(int i)
    {
        RemoteViews remoteViews = new RemoteViews(applicationContext.getPackageName(), R.layout.widget_item_view);

        ItemDetails itemDetails = itemDetailsList.get(i);
        remoteViews.setTextViewText(R.id.TaskName, itemDetails.getTitle());
        remoteViews.setTextViewText(R.id.TaskTime, getDateString(itemDetails));

        return remoteViews;
    }

    private String getDateString(ItemDetails itemDetails)
    {
        Time notificationDate = itemDetails.getNotificationDate();

        return notificationDate.format("%Y:%m:%d %H:%M:%S");
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }
}
