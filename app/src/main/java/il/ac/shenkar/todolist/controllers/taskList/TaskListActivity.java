package il.ac.shenkar.todolist.controllers.taskList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import javax.inject.Inject;

import il.ac.shenkar.todolist.ItemDetails;
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
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete all?");
        alert.setMessage("Are you sure you want to delete all finished tasks ?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                try
                {
                    for (int x = 0; x < listView.getChildCount(); x++)
                    {
                        View childAt = listView.getChildAt(x);
                        //noinspection ConstantConditions
                        if (childAt != null && (Boolean)childAt.getTag(R.integer.bla))
                        {
                            //noinspection ConstantConditions
                            Integer id = (Integer) childAt.getTag(R.integer.id);
                            ItemDetails itemDetailsC = dataBaseConnector.getElmById(id).get();
                            itemDetailsC.setStatus();
                            dataBaseConnector.updateItemDetails(itemDetailsC);

                            dataBaseConnector.deleteElmId(id);
                        }
                    }
                    updateData();
                    dialog.dismiss();
                }
                catch (Exception e){}
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}