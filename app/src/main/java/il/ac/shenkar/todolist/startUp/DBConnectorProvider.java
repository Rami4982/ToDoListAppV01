package il.ac.shenkar.todolist.startUp;

import android.content.Context;

public class DBConnectorProvider
{
    private static DataBaseConnector dataBaseConnector;

    public static DataBaseConnector get(Context context)
    {
        if (dataBaseConnector == null)
        {
            dataBaseConnector = new DataBaseConnector(context);
            new DataBasePopulator().populate(dataBaseConnector);
        }

        return dataBaseConnector;
    }
}
