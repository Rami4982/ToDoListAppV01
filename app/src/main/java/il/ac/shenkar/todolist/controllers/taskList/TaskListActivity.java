package il.ac.shenkar.todolist.controllers.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import javax.inject.Inject;

import il.ac.shenkar.todolist.R;
import il.ac.shenkar.todolist.controllers.createTask.CreateTaskActivity;
import il.ac.shenkar.todolist.controllers.utils.ActivityOpenerFactory;
import il.ac.shenkar.todolist.startUp.DataBaseConnector;
import il.ac.shenkar.todolist.startUp.activitys.RoboAnalyticActivity;
import roboguice.inject.InjectView;

public class TaskListActivity extends RoboAnalyticActivity
{
    @Inject
    ItemListBaseAdapter itemListBaseAdapter;
    @Inject
    MenuInflater menuInflater;
    @Inject
    DataBaseConnector dataBaseConnector;

    @InjectView(R.id.taskList_main)
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView.setAdapter(itemListBaseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    public void onNewToDoClick(MenuItem menuItem)
    {
        Intent intent = new Intent(this, CreateTaskActivity.class);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        updateData();
    }

    private void updateData()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                itemListBaseAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onDeleteAll(MenuItem item)
    {
        dataBaseConnector.deleteAll();

        updateData();
    }
}