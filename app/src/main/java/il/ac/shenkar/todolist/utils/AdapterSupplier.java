package il.ac.shenkar.todolist.utils;

import android.widget.BaseAdapter;

import com.google.common.base.Supplier;

import java.io.Serializable;

public class AdapterSupplier implements Supplier<BaseAdapter>, Serializable
{
    private final BaseAdapter baseAdapter;

    public AdapterSupplier(BaseAdapter baseAdapter)
    {
        this.baseAdapter = baseAdapter;
    }

    @Override
    public BaseAdapter get()
    {
        return baseAdapter;
    }
}
