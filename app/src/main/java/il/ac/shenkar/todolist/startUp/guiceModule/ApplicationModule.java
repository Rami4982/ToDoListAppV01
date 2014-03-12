package il.ac.shenkar.todolist.startUp.guiceModule;

import android.view.MenuInflater;

import com.google.inject.Binder;
import com.google.inject.Module;

import il.ac.shenkar.todolist.startUp.DataBaseConnector;

public class ApplicationModule implements Module
{
    private final DataBaseConnector databaseConnector;

    public ApplicationModule(DataBaseConnector databaseConnector)
    {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void configure(Binder binder)
    {
        binder.bind(DataBaseConnector.class).toInstance(databaseConnector);
        binder.bind(MenuInflater.class).toProvider(MenuInflaterProvider.class);
    }
}
