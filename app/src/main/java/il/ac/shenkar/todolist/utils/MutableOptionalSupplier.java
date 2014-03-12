package il.ac.shenkar.todolist.utils;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import java.io.Serializable;

import il.ac.shenkar.todolist.ItemDetails;

public class MutableOptionalSupplier<T> implements Supplier<T>, Serializable
{
    private Optional<T> optional = Optional.absent();

    public MutableOptionalSupplier()
    {
        this.optional = Optional.absent();
    }

    @Override
    public T get()
    {
        return optional.get();
    }

    public boolean isPresent()
    {
        return optional.isPresent();
    }

    public static <V> MutableOptionalSupplier<V> of(V reference)
    {
        MutableOptionalSupplier<V> mutableOptionalSupplier = new MutableOptionalSupplier<V>();
        mutableOptionalSupplier.set(Optional.of(reference));

        return mutableOptionalSupplier;
    }

    public void set(Optional<T> optional)
    {
        this.optional = optional;
    }

    public T or(T t)
    {
        return optional.or(t);
    }

    public T orNull()
    {
        return optional.orNull();
    }

    public static <T> MutableOptionalSupplier<T> absent()
    {
        return new MutableOptionalSupplier<T>();
    }

    public void set(T itemDetails)
    {
        set(Optional.of(itemDetails));
    }
}
