package il.ac.shenkar.todolist.controllers.utils;

import android.app.Activity;
import android.content.Context;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ActivityOpenerFactory
{
    private final Context context;

    @Inject
    public ActivityOpenerFactory(Context context)
    {
        this.context = context;
    }

    public <A extends Activity> ActivityOpener createFrom(Class<A> activityClass)
    {
        return new ActivityOpener<A>(context, activityClass);
    }
}
