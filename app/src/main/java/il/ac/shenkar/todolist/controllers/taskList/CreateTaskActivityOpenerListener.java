package il.ac.shenkar.todolist.controllers.taskList;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import javax.inject.Inject;

import il.ac.shenkar.todolist.controllers.createTask.CreateTaskActivity;
import roboguice.inject.ContextSingleton;

@ContextSingleton
class CreateTaskActivityOpenerListener implements View.OnClickListener
{
    @Inject
    Context context;

    @Override
    public void onClick(View v)
    {
        context.startActivity(new Intent(context, CreateTaskActivity.class));
    }
}
