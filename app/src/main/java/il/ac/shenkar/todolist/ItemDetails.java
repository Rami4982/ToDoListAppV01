package il.ac.shenkar.todolist;

import android.text.format.Time;

/**
 * Created by rami on 07/12/13.
 */
public class ItemDetails
{
    private int id;
    private String title;
    private String description;
    private Time creationDate;
    private Time notiDate;
    private String location;
    private boolean status;


    public ItemDetails()
    {
        this.id = 0;
        this.title = "";
        this.status = true;
        this.description = "";
        creationDate = new Time();
        creationDate.setToNow();
        notiDate = new Time();
        notiDate.setToNow();
        location = "";
    }

    public ItemDetails(int id, String title, String description)
    {
        this.id = id;
        this.title = title;
        this.status = true;
        this.description = description;
        this.creationDate = new Time();
        this.creationDate.setToNow();
        this.notiDate = new Time();
        this.notiDate.setToNow();
        this.location = "";
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public boolean getStatus() { return status; }

    public void setStatus() { this.status = !this.status; }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "ItemDetails{" + "id=" + id + ", title='" + title + '\'' + ", status='" + status + '\'' + ", description='" + description + '\'' + '}';
    }
}