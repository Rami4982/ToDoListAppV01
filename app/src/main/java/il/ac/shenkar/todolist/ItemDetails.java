package il.ac.shenkar.todolist;

import android.text.format.Time;

import com.google.common.base.Objects;

import java.io.Serializable;

public class ItemDetails implements Serializable
{
    private final int id;
    private String title;
    private String description;
    private Time creationDate;
    private Time notificationDate;
    private String location;
    private boolean status;

    public ItemDetails()
    {
        this(0, "", false, "");
    }

    public ItemDetails(int id, String title, boolean status, String description)
    {
        this.id = id;
        this.title = title;
        this.status = status;
        this.description = description;
        this.creationDate = new Time();
        this.creationDate.setToNow();
        this.notificationDate = new Time();
        this.notificationDate.setToNow();
        this.location = "";
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {

        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean getStatus()
    {
        return status;
    }

    public void setStatus()
    {
        this.status = !this.status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Time getCreationDate()
    {
        return creationDate;
    }

    public Time getNotificationDate()
    {
        return notificationDate;
    }

    public String getLocation()
    {
        return location;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("status", status)
                .add("description", description)
                .toString();
    }
}