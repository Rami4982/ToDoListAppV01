package il.ac.shenkar.todolist.controllers.taskList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.inject.Inject;

import java.io.Serializable;

import il.ac.shenkar.todolist.controllers.createTask.CreateTaskActivity;
import il.ac.shenkar.todolist.ItemDetails;
import il.ac.shenkar.todolist.R;
import il.ac.shenkar.todolist.startUp.DataBaseConnector;
import il.ac.shenkar.todolist.utils.OnSwipeTouchListener;
import roboguice.inject.ContextSingleton;

@ContextSingleton
class ItemListBaseAdapter extends BaseAdapter implements Serializable
{
    private final Activity context;
    private final DataBaseConnector connectorDB;
    private LayoutInflater l_Inflater;
    public static String EXTRA_IDELM = "il.ac.shenkar.todolist.IDELM";

    @Inject
    ItemListBaseAdapter(Activity context, DataBaseConnector connectorDB)
    {
        this.context = context;
        this.connectorDB = connectorDB;
        l_Inflater = LayoutInflater.from(context);
    }

    public int getCount() { return connectorDB.getSize(); }

    public Object getItem(int position) { return connectorDB.getElm(position); }

    public long getItemId(int position) { return connectorDB.getElmID(position); }

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
            View parent = (View) view.getParent();

            Boolean g = (Boolean)parent.getTag(R.integer.bla);
            if (!g)
            {
                parent.setAlpha(0.5f);
            }
            else
            {
                parent.setAlpha(1f);
            }

            //noinspection ConstantConditions
            parent.setTag(R.integer.bla, !g);
        }
    };

    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int positionFinal = position;

        ViewHolder holder;
        ItemDetails itemDetails = connectorDB.getElm(position).get();

        if ( convertView == null)
        {
            convertView = l_Inflater.inflate(R.layout.item_details_view, null);
            holder = new ViewHolder();

            holder.Title = (TextView) convertView.findViewById(R.id.Name);
            holder.Title.setOnClickListener(editButtonOnClickListener);

            holder.Confirm = (ImageButton) convertView.findViewById(R.id.Confirm);
            holder.Confirm.setOnClickListener(doneButtonOnClickListener);

            /*holder.Title.setOnTouchListener(new OnSwipeTouchListener(context)
            {
                @Override
                public void onSwipeLeft()
                {
                    doneButtonOnClickListener.onClick(getViewMutableOptionalSupplier().get());
                }

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    boolean b = super.onTouch(view, motionEvent);

                    Intent intent = new Intent(context, CreateTaskActivity.class);
                    intent.putExtra("urlIndex", (Integer) view.getTag());
                    context.startActivity(intent);

                    return b;
                }
            });*/
            convertView.setTag(R.integer.bla, false);
            convertView.setTag(R.integer.id, itemDetails.getId());
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Title.setTag(position);
        holder.Confirm.setTag(position);
        holder.Title.setText(itemDetails.getTitle());


        return convertView;
    }

    private static class ViewHolder
    {
        TextView Title;
        ImageButton Confirm;
    }
}