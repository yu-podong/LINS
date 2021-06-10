package com.yupodong.lins;

import android.app.job.JobParameters;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.firebase.jobdispatcher.JobService;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

public class MyJobService extends JobService
{
    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}