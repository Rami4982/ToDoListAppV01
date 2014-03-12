package il.ac.shenkar.todolist.time;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.Time;

import com.google.common.base.Supplier;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    private final Activity activity;
    private final Supplier<Time> timeSupplier;

    DatePicker(Activity activity, Supplier<Time> timeSupplier)
    {
        this.activity = activity;
        this.timeSupplier = timeSupplier;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Time time = timeSupplier.get();

        return new DatePickerDialog(activity, this, time.year, time.month, time.monthDay);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day)
    {
        timeSupplier.get().set(day, month, year);
    }
}
