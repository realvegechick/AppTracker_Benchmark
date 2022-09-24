package fudan.secsys.benchmark.ui.main;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fudan.secsys.benchmark.R;
import fudan.secsys.benchmark.utils.EnvCheckUtils;

public class MyRunner implements Runnable {
    public static final String TAG="AppTracker_Benchmark";
    final TextView view;
    Handler handler;
    public static int cnt=0;
    static int total;
    int start=0;
    int end=0;
    MyRunner(Handler handle,TextView tv,int n,int s, int e){
        handler=handle;
        view=tv;
        total=n;
        start=s;
        end=e;

    }
    @Override
    public void run() {
        EnvCheckUtils.checkFrida(view,start,end);
        cnt+=1;
        Log.i(TAG,"thread finished:"+cnt);
        if(cnt>=total){
            handler.sendEmptyMessage(0);
            if(view.getText().toString().contains("detecting")){
                view.setTextColor(view.getContext().getResources().getColor(R.color.green));
                view.setText("Frida: false");
            }
        }
    }
}
