package il.ac.shenkar.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rami on 07/12/13.
 */

public class ItemListBaseAdapter extends BaseAdapter
{
    //    private static ArrayList<ItemDetails> itemDetailsrrayList;
    private Context context;
    private LayoutInflater l_Inflater;
    private Connect_DB connectorDB = null;
    public static String EXTRA_IDELM = "il.ac.shenkar.todolist.IDELM";

    public ItemListBaseAdapter(Context context)
    {
        connectorDB = Connect_DB.getInstance(context);
        l_Inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() { return connectorDB.getSize(); }

    public Object getItem(int position) { return connectorDB.getElm(position); }

    public long getItemId(int position) { return connectorDB.getElmID(position); }

    private final View.OnClickListener delButtonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            int position = (Integer) view.getTag();
            connectorDB.deleteElm(position);
            notifyDataSetChanged();
        }
    };
    private final View.OnClickListener editButtonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(context, CreateTaskActivity.class);
            intent.putExtra("urlIndex", (Integer) view.getTag());
            context.startActivity(intent);

        }
    };
    private final View.OnClickListener doneButtonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {

            ItemDetails itemDetailsC = connectorDB.getElm((Integer) view.getTag());
            itemDetailsC.setStatus();
            connectorDB.editElm(itemDetailsC);
            Toast.makeText( context," is  " +(Integer) view.getTag(),10 ).show();

        }
    };

    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int positionFinal = position;
        ViewHolder holder;
        if ( convertView == null )
        {
            convertView = l_Inflater.inflate(R.layout.item_details_view, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.name);
            holder.editBtn = (Button) convertView.findViewById(R.id.edit_Btn);
            holder.editBtn.setOnClickListener(editButtonOnClickListener);
            holder.doneBtn = (CheckBox) convertView.findViewById(R.id.Done_Btn);
            holder.doneBtn.setChecked(connectorDB.getElm(position).getStatus());
            holder.doneBtn .setOnClickListener(doneButtonOnClickListener);
            holder.delBtn = (Button) convertView.findViewById(R.id.Del_Btn);
            holder.delBtn.setOnClickListener(delButtonOnClickListener);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.doneBtn.setTag(position);
        holder.delBtn.setTag(position);
        holder.editBtn.setTag(position);
        holder.tvTitle.setText(connectorDB.getElm(position).getTitle());


        return convertView;
    }

    private static class ViewHolder
    {
        TextView tvTitle;
        Button delBtn, editBtn;
        CheckBox doneBtn;

    }
}
//            connectorDB.deleteElm(positionFinal);

//        holder.delBtn.setText(R.id.Del_Btn);
//public Object getItem (int position)
//{
//    //return connectorDB.getItems().get(position);
//    if (position == 0) position=1;
////        return connectorDB.getElm(position);
//    return connectorDB.getElm(position);
//    //return null;
//
//}
//        holder.delBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v)
//            {
//                //
//                connectorDB.deleteElm(positionFinal);
//                notifyDataSetChanged();
//            }
//        });

//Toast.makeText( context," is  "+position ,10 ).show();