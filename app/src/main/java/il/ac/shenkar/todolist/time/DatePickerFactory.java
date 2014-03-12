package il.ac.shenkar.todolist.time;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.Time;

import com.google.common.base.Supplier;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class DatePickerFactory
{
    private final Activity activity;

    @Inject
    DatePickerFactory(Activity activity)
    {
        this.activity = activity;
    }

    public DatePicker createFrom(Supplier<Time> timeSupplier)
    {
        return new DatePicker(activity, timeSupplier);
    }
}
