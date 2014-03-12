package il.ac.shenkar.todolist.utils;

import android.app.Activity;
import android.widget.BaseAdapter;

import java.io.Serializable;

public class AdapterNotifier implements Serializable
{
    private final Activity activity;
    private final BaseAdapter baseAdapter;

    AdapterNotifier(Activity activity, BaseAdapter baseAdapter)
    {
        this.activity = activity;
        this.baseAdapter = baseAdapter;
    }

    public void notifyAdapter()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                baseAdapter.notifyDataSetChanged();
            }
        });
    }
}
