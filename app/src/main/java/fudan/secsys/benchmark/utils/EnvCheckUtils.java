package fudan.secsys.benchmark.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        return android.os.Build.TAGS+":"+android.os.Build.TYPE;
    }
    static public void checkFrida(TextView tv,int start,int end){
        Socket socket;
        for(int i=start; i<end;i++){
            try {
                socket = new Socket(InetAddress.getLocalHost(), i);
                socket.setSoTimeout(1000);
                InputStream input=socket.getInputStream();
                OutputStream output=socket.getOutputStream();
                output.write("\00".getBytes());
                output.write("AUTH\r\n".getBytes());
                socket.shutdownOutput();
                byte[] buf = new byte[1024];
                input.read(buf);
                String s=new String(buf);
                Log.i(TAG,s);
                if(s.contains("Content-Length: 0")){
                    tv.setTextColor(tv.getContext().getResources().getColor(R.color.red));
                    tv.setText("Frida: server running on port "+i);
                    return;
                }
            }catch (Exception x) {
            }
        }
    }
    static public boolean checkMagisk(){
        try {
            Process ps=Runtime.getRuntime().exec("mount");
            ps.waitFor();
            InputStream in=ps.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=read.readLine())!=null) {
                Log.i(TAG,line);
                if (line.contains("magisk")||line.contains("/dev/root")) {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    static public boolean checkEmulator(Context context){
        int suspectCount = 0;
        String baseBandVersion = getProperty("gsm.version.baseband");
        if (null == baseBandVersion || baseBandVersion.contains("1.0.0.0"))
            ++suspectCount;//基带信息

        String buildFlavor = getProperty("ro.build.flavor");
        if (null == buildFlavor || buildFlavor.toLowerCase().contains("vbox") || buildFlavor.toLowerCase().contains("sdk_gphone")|| buildFlavor.toLowerCase().contains("x86"))
            ++suspectCount;//渠道

        String model = getProperty("ro.product.model");
        if (null == model )
            ++suspectCount;//模型
        else if (model.toLowerCase().contains("google_sdk")||model.toLowerCase().contains("emulator")||model.toLowerCase().contains("android sdk built for x86")) suspectCount += 10;

        String productBoard = getProperty("ro.product.board");
        if (null == productBoard || productBoard.contains("android"))
            ++suspectCount;//芯片
        else if (productBoard.toLowerCase().contains("goldfish")) suspectCount += 10;//AVD

        String manufacturer = getProperty("ro.product.manufacturer");
        if (null == manufacturer || manufacturer.toLowerCase().contains("unknown"))
            ++suspectCount;//制造商
        else if (manufacturer.toLowerCase().contains("genymotion")) suspectCount += 10;//天天
        else if (manufacturer.toLowerCase().contains("netease")) suspectCount += 10;//网易mumu

        String boardPlatform = getProperty("ro.board.platform");
        if (null == boardPlatform || boardPlatform.contains("android") )
            ++suspectCount;//芯片平台
        else if (boardPlatform.toLowerCase().contains("ranchu")) suspectCount += 10;//AVD

        String hardWare = getProperty("ro.hardware");
        if (null == hardWare) ++suspectCount;
        else if (hardWare.toLowerCase().contains("ttvm")) suspectCount += 10;//天天
        else if (hardWare.toLowerCase().contains("nox")) suspectCount += 10;//夜神

        String cameraFlash = "";
        String sensorNum = "sensorNum: ";
        String bluetooth = "";
        if (context != null) {
            boolean isSupportCameraFlash = context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");//是否支持闪光灯
            if (!isSupportCameraFlash) ++suspectCount;
            cameraFlash = isSupportCameraFlash ? "support CameraFlash" : "no CameraFlash";

            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            int sensorSize = sm.getSensorList(Sensor.TYPE_ALL).size();
            if (sensorSize < 10) ++suspectCount;//传感器个数
            sensorNum = sensorNum + sensorSize;

            boolean isSupportBluetooth =context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
            if(!isSupportCameraFlash)//是否支持蓝牙
                ++suspectCount;
            bluetooth = isSupportBluetooth? "support Bluethooth" : "no Bluethooth";
        }


        StringBuffer stringBuffer = new StringBuffer("Properties: ")
                .append(baseBandVersion).append("|")
                .append(buildFlavor).append("|")
                .append(model).append("|")
                .append(productBoard).append("|")
                .append(manufacturer).append("|")
                .append(boardPlatform).append("|")
                .append(hardWare).append("|")
                .append(cameraFlash).append("|")
                .append(bluetooth).append("|")
                .append(sensorNum).append("|");
        Log.i(TAG,stringBuffer.toString());
        return suspectCount > 2;
    }
    static public String getProperty(String propName) {
        String value = null;
        Object roSecureObj;
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke(null, propName);
            if (roSecureObj != null) value = (String) roSecureObj;
        } catch (Exception e) {
            value = null;
        } finally {
            return value;
        }
    }

}
