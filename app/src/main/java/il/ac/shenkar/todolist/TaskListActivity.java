package il.ac.shenkar.todolist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ListView;

public class TaskListActivity extends Activity
{

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ListView listView = (ListView) findViewById(R.id.listV_main);
        Connect_DB connectorDB = Connect_DB.getInstance(this);
        if (connectorDB.getSize() == 0)
            connectorDB.populateItemsArr();

        listView.setAdapter(new ItemListBaseAdapter(this));
        Button addBtn = (Button) findViewById(R.id.add_Btn);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                startActivity(new Intent(TaskListActivity.this, CreateTaskActivity.class));
            }
        });
        Button close_Button = (Button) this.findViewById(R.id.close_Btn);
        close_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }
}
//Context context=getApplicationContext();
//        Toast.makeText(this,"ss",Toast.LENGTH_LONG).show();
//if(connectorDB.getSize()==0)

// connectorDB.addItem(new ItemDetails(0,"enter todo activities","done","60"));
//else
//          connectorDB.deleteInstruction();
//     List<ItemDetails> contacts = connectorDB.getItems();
//        for (ItemDetails cn : contacts)
//        {
//            Toast.makeText(this," are = "+cn,100).show();
//        }
//Toast.makeText(this," are = "+connectorDB.getElm(1),100).show();
//Toast.makeText(this," are = "+connectorDB.getSize(),100).show();
