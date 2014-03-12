package il.ac.shenkar.todolist.startUp;

import android.app.Application;

import com.google.inject.Inject;

import il.ac.shenkar.todolist.startUp.guiceModule.ApplicationModule;
import roboguice.RoboGuice;

public class ToDoListApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        RoboGuice.setBaseApplicationInjector
                (
                        this,
                        RoboGuice.DEFAULT_STAGE,
                        RoboGuice.newDefaultRoboModule(this),
                        new ApplicationModule(DBConnectorProvider.get(getApplicationContext()))
                );
    }
}
