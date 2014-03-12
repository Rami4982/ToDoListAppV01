package il.ac.shenkar.todolist.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import javax.inject.Inject;

import il.ac.shenkar.todolist.startUp.DataBaseConnector;

public class TaskListService extends RemoteViewsService
{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return new TasksViewAdapterFactory(this.getApplicationContext(), intent);
    }
}
