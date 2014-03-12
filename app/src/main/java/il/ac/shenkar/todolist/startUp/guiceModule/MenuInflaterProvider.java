package il.ac.shenkar.todolist.startUp.guiceModule;

import android.app.Activity;
import android.view.MenuInflater;

import com.google.inject.Provider;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

@ContextSingleton
class MenuInflaterProvider implements Provider<MenuInflater>
{
    @Inject
    Activity activity;

    @Override
    public MenuInflater get()
    {
        return activity.getMenuInflater();
    }
}
