package il.ac.shenkar.todolist.controllers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityOpener<A extends Activity>
{
    private final Context context;
    private final Class<A> activityClass;

    ActivityOpener(Context context, Class<A> activityClass)
    {
        this.context = context;
        this.activityClass = activityClass;
    }

    public void open()
    {
        context.startActivity(new Intent(context, activityClass));
    }
}
