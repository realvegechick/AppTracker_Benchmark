package fudan.secsys.benchmark.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import fudan.secsys.benchmark.R;

public class EnvCheckUtils {
    public static final String TAG="AppTracker_Benchmark";
    static public boolean checkRoot(){
        File file = null;
        String[] paths = {
                "/su", "/su/bin/su", "/sbin/su",
                "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su",
                "/system/xbin/su",
                "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su",
                "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr",
                "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd",
                "/system/bin/conbb", "/system/xbin/conbb"};
        try {
            file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                Log.i(TAG,"/system/app/Superuser.apk exist");
                return true;
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        try{
            int dirCount = paths.length;
            for (int i = 0; i < dirCount; ++i) {
                String dir = paths[i];
                if ((new File(dir)).exists()) {
                    return true;
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return false;
    }
    static public String checkBuild(){
        String buildTags = "Build: "+android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return buildTags;
        }
        return buildTags;
    }
    static public void checkFrida(TextView tv){
        Socket socket;
        for(int i=0; i<=65537;i++){
            try {
                Log.i(TAG,"port:"+i);
                socket = new Socket(InetAddress.getLocalHost(), i);
                socket.setSoTimeout(1000);
                InputStream input=socket.getInputStream();
                OutputStream output=socket.getOutputStream();
                try {
                    output.write("\00".getBytes());
                    output.write("AUTH\r\n".getBytes());
                    byte[] buf = new byte[1024];
                    input.read(buf);
                }catch (Exception e){
                    tv.setTextColor(tv.getContext().getResources().getColor(R.color.red));
                    tv.setText("Frida: server running on port "+i);
                    return;
                }
            }catch (Exception x) {

            }
        }
        tv.setTextColor(tv.getContext().getResources().getColor(R.color.green));
        tv.setText("FRIDA: false");
    }


}
