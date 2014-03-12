
package il.ac.shenkar.todolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTaskActivity extends Activity
{
    Connect_DB connectorDB = Connect_DB.getInstance(this);
    public static String EXTRA_MESSAGE = "il.ac.shenkar.todolist.MESSAGE";
    private String titleTask, descTask;
    private int index = -1;
    private DatePicker datePicker;
    private TimePicker timePicker;
    ItemDetails itemDetailsC = null;
    private EditText editText, inputDescription;
    private boolean updateMode = false;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        descTask = titleTask = "";
        index = (int) getIntent().getIntExtra("urlIndex", -1);
        editText = (EditText) findViewById(R.id.edit_message);
        inputDescription = (EditText) findViewById(R.id.inputDescription);
        if ( index > -1 )    // Create mode
        {
            ((Button) this.findViewById(R.id.create_Btn)).setText("Edit");
            itemDetailsC = connectorDB.getElm(index);
            editText.setText(itemDetailsC.getTitle());
            inputDescription.setText(itemDetailsC.getDescription());
        }
        ((Button) findViewById(R.id.loc_Btn)).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                populateIt(v);

                Intent intentSent = new Intent(getApplicationContext(), LocationActivity.class);
                intentSent.putExtra(EXTRA_MESSAGE, titleTask);
                  startActivity(intentSent);
            }
        });
        Button createBtn = (Button) findViewById(R.id.create_Btn);
        createBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                populateIt(v);
                startActivity(new Intent(CreateTaskActivity.this, TaskListActivity.class));

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

    private void populateIt(View v)
    {
        titleTask = (editText.getText().toString() != "") ? editText.getText().toString() : "";
        descTask = (inputDescription.getText().toString() != "") ? inputDescription.getText().toString() : "";
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);


        if ( index > -1 )    // edit
        {
            itemDetailsC.setTitle(titleTask);
            itemDetailsC.setDescription(descTask);
            connectorDB.editElm(itemDetailsC);

        }
        else if ( titleTask != "" ) // Insert
        {
            connectorDB.addItem(new ItemDetails(0, titleTask, descTask));
        }
        createNotification(v);
    }

    public void createNotification(View view)
    {
        Context context = getApplicationContext();
        Intent intentSent = new Intent(this, MyBroadcastReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
        long expireTime = calendar.getTimeInMillis() - System.currentTimeMillis();
        if ( expireTime > 5000 )
        {//only future notification for more than 1 minute
            intentSent.putExtra(EXTRA_MESSAGE, titleTask);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intentSent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);//
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + expireTime, pendingIntent2);
        }
    }
}
//
//            Time now = new Time();
//            now.setToNow();
////            if(ed.getNotificationDate().toMillis(false) > now.toMillis(false))
////            {
////                timeSwitch.setChecked(true);
////            }
////            if(ed.getLocation() != "" && ed.getLocation() != null)
////            {
////                locationSwitch.setChecked(true);
////            }

//

//android:layout_weight="1" android:layout_width="0dp"
//Toast.makeText(this, "Alarm set in " + expireTime + " seconds", Toast.LENGTH_LONG).show();
//        Log.d();
//     Intent intentSent = new Intent(this, MyBroadcastReceiver.class);