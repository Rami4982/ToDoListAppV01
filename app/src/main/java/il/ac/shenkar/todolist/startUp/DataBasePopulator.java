package il.ac.shenkar.todolist.startUp;

class DataBasePopulator
{
    void populate(DataBaseConnector dataBaseConnector)
    {
        dataBaseConnector.populateItemsArr();
    }
}
