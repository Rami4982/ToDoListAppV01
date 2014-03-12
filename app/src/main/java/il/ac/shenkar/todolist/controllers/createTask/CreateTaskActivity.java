
package il.ac.shenkar.todolist.controllers.createTask;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import javax.inject.Inject;

import il.ac.shenkar.todolist.ItemDetails;
import il.ac.shenkar.todolist.MyBroadcastReceiver;
import il.ac.shenkar.todolist.R;
import il.ac.shenkar.todolist.controllers.Extras;
import il.ac.shenkar.todolist.controllers.location.LocationActivity;
import il.ac.shenkar.todolist.startUp.DataBaseConnector;
import il.ac.shenkar.todolist.startUp.activitys.RoboFragmentAnalyticActivity;
import il.ac.shenkar.todolist.time.DatePicker;
import il.ac.shenkar.todolist.time.DatePickerFactory;
import il.ac.shenkar.todolist.time.TimePicker;
import il.ac.shenkar.todolist.time.TimePickerFactory;
import il.ac.shenkar.todolist.utils.MutableOptionalSupplier;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class CreateTaskActivity extends RoboFragmentAnalyticActivity
{
    @InjectView(R.id.edit_message)
    EditText title;
    @InjectView(R.id.inputDescription)
    EditText description;
    @InjectView(R.id.clockButton)
    ImageButton clockButton;
    @InjectView(R.id.mapButton)
    ImageButton mapButton;
    @InjectView(R.id.saveButton)
    Button saveButton;

    @InjectExtra(value = "urlIndex", optional = true)
    Integer index = -1;

    @Inject
    DataBaseConnector connectorDB;
    @Inject
    TimePickerFactory timePickerFactory;
    @Inject
    DatePickerFactory datePickerFactory;

    private final MutableOptionalSupplier<ItemDetails> itemDetailsSupplier = MutableOptionalSupplier.absent();

    private boolean updateMode = false;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        initializeIfExists();
    }

    private void initializeIfExists()
    {
        updateMode = index != -1;

        if (updateMode)
        {
            Optional<ItemDetails> detailsOptional = connectorDB.getElm(index);
            itemDetailsSupplier.set(detailsOptional);

            title.setText(detailsOptional.get().getTitle());
            description.setText(detailsOptional.get().getDescription());
        }
        else
        {
            itemDetailsSupplier.set(new ItemDetails());
        }
    }

    public void onMapClick(View view)
    {
        startActivityFor(LocationActivity.class);
    }

    public void onClockClick(View view)
    {
        Supplier<Time> timeSupplier = Suppliers.ofInstance(itemDetailsSupplier.get().getNotificationDate());
        TimePicker timePicker = timePickerFactory.createFrom(timeSupplier);
        DatePicker datePicker = datePickerFactory.createFrom(timeSupplier);


        timePicker.show(getFragmentManager(), "timePicker");
        datePicker.show(getFragmentManager(), "datePicker");
    }

    private void startActivityFor(Class<?> clazz)
    {
        Intent intentSent = new Intent(getApplicationContext(), clazz);

        startActivity(intentSent);
    }

    public void onSaveClick(View view)
    {
        populateAndSave();
        finish();
    }

    private void populateAndSave()
    {
        if (itemDetailsSupplier.isPresent())
        {
            ItemDetails itemDetails = itemDetailsSupplier.get();
            //noinspection ConstantConditions
            itemDetails.setTitle(title.getText().toString());
            //noinspection ConstantConditions
            itemDetails.setDescription(description.getText().toString());

            if (updateMode)
            {
                connectorDB.updateItemDetails(itemDetails);
            }
            else
            {
                connectorDB.addItem(itemDetails);
            }
        }

        createNotification();
    }

    public void createNotification()
    {
        Context context = getApplicationContext();
        Intent intentSent = new Intent(this, MyBroadcastReceiver.class);

        Time notificationDate = itemDetailsSupplier.get().getNotificationDate();
        long expireTime = notificationDate.toMillis(false) - System.currentTimeMillis();

        if (expireTime > 5000)
        {//only future notification for more than 1 minute
            //      intentSent.putExtra(EXTRA_MESSAGE, titleTask);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intentSent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);//
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + expireTime, pendingIntent2);
        }
    }
}