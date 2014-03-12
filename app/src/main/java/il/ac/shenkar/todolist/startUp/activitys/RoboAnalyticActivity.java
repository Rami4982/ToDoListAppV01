package il.ac.shenkar.todolist.startUp.activitys;

import com.google.analytics.tracking.android.EasyTracker;

import roboguice.activity.RoboActivity;

public class RoboAnalyticActivity extends RoboActivity
{
    @Override
    protected void onStart()
    {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
