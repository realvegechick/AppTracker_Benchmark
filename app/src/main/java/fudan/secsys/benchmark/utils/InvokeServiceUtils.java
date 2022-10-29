package fudan.secsys.benchmark.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.biometrics.BiometricManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InvokeServiceUtils {
    private static void showDialog(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("RESULT");
        builder.setMessage(msg);
        builder.setPositiveButton("back", null);
        builder.create().show();
    }

    public static boolean callVibratorService(Context context) {
        //Toast.makeText(context, "Test VibratorService!!!", Toast.LENGTH_SHORT).show();
        Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        final boolean ret = mVibrator.hasVibrator();
        //Toast.makeText(context, "Invoke hasVibrator() method! Return: " + ret, Toast.LENGTH_SHORT).show();

        mVibrator.vibrate(1000);
        //Toast.makeText(context, "Invoke vibrate() method!", Toast.LENGTH_SHORT).show();

        /*long[] inp = {0, 1000, 2000, 3000};
        mVibrator.vibrate(inp, 2);
        Toast.makeText(context, "Invoke vibrate() method!", Toast.LENGTH_LONG).show();*/

        String msg = "Test VibratorService!!!" + "\n" + "Invoke hasVibrator() method! -> Return: " + ret + "\n" + "Invoke vibrate() method!";
        showDialog(context, msg);

        return true;
    }


    public static boolean callClipboardService(Context context) {
        //Toast.makeText(context, "Test ClipboardService!!!", Toast.LENGTH_SHORT).show();
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        final boolean ret = mClipboardManager.hasPrimaryClip();
        //Toast.makeText(context, "Invoke hasPrimaryClip() method! Return: " + ret, Toast.LENGTH_SHORT).show();

        ClipData mClipData = mClipboardManager.getPrimaryClip();
        //Toast.makeText(context, "Invoke getPrimaryClip() method! Return: " + mClipData, Toast.LENGTH_SHORT).show();

        mClipboardManager.clearPrimaryClip();
        //Toast.makeText(context, "Invoke clearPrimaryClip() method!", Toast.LENGTH_SHORT).show();

        String msg = "Test ClipboardService!!!" + "\n" + "Invoke hasPrimaryClip() method! -> Return: " + ret + "\n"
                + "Invoke getPrimaryClip() method! -> Return: " + mClipData + "\n"
                + "Invoke clearPrimaryClip() method!";
        showDialog(context, msg);

        return true;
    }

    public static boolean callPowerManagerService(Context context) {
        //Toast.makeText(context, "Test PowerManagerService!!!", Toast.LENGTH_SHORT).show();
        PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        final boolean ret = mPowerManager.isScreenOn();
        //Toast.makeText(context, "Invoke isScreenOn() method! Return: " + ret, Toast.LENGTH_SHORT).show();

        final boolean ret2 = mPowerManager.isPowerSaveMode();
        //Toast.makeText(context, "Invoke isPowerSaveMode() method! Return: " + ret2, Toast.LENGTH_SHORT).show();

        String msg = "Test PowerManagerService!!!" + "\n" + "Invoke isScreenOn() method! -> Return: " + ret + "\n"
                + "Invoke isPowerSaveMode() method! -> Return: " + ret2;
        showDialog(context, msg);

        return true;
    }

    public static boolean callActivityManagerService(Context context) {
        //Toast.makeText(context, "Test ActivityManagerService!!!", Toast.LENGTH_SHORT).show();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final boolean ret = mActivityManager.isLowRamDevice();
        //Toast.makeText(context, "Invoke isLowRamDevice() method! Return: " + ret, Toast.LENGTH_SHORT).show();

        //Toast.makeText(context, "Invoke getAppTasks() method! Return: " + mActivityManager.getAppTasks(), Toast.LENGTH_SHORT).show();

        //Toast.makeText(context, "Invoke getDeviceConfigurationInfo() method! Return: " + mActivityManager.getDeviceConfigurationInfo(), Toast.LENGTH_SHORT).show();

        String msg = "Test ActivityManagerService!!!" + "\n" + "Invoke isLowRamDevice() method! -> Return: " + ret + "\n"
                + "Invoke getAppTasks() method! -> Return: " + mActivityManager.getAppTasks() + "\n"
                + "Invoke getDeviceConfigurationInfo() method! -> Return: " + mActivityManager.getDeviceConfigurationInfo();
        showDialog(context, msg);

        return true;
    }

    public static boolean callLocationManagerService(Context context) {
        //Toast.makeText(context, "Test LocationManagerService!!!", Toast.LENGTH_SHORT).show();
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //Toast.makeText(context, "Invoke getAllProviders() method! Return: "+mLocationManager.getAllProviders(),Toast.LENGTH_SHORT).show();

            //Toast.makeText(context, "Invoke isProviderEnabled() method! Return: "+mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER),Toast.LENGTH_SHORT).show();

            //Location mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Toast.makeText(context, "Invoke getLastKnownLocation() method! Return: "+mLocation, Toast.LENGTH_SHORT).show();

            String msg = "Test LocationManagerService!!!" + "\n"
                    + "Invoke getAllProviders() method! -> Return: " + mLocationManager.getAllProviders() + "\n"
                    + "Invoke isProviderEnabled() method! -> Return: " + mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            showDialog(context, msg);

            return true;
        }

        return false;
    }

    public static boolean callWifiService(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        boolean wifiEnabled = mWifiManager.isWifiEnabled();
        int wifiState = mWifiManager.getWifiState();
        WifiInfo wifiinfo = mWifiManager.getConnectionInfo();
        String wifiName = wifiinfo.getSSID();
        int IPAddress = wifiinfo.getIpAddress();
        String IP = new StringBuilder().append(IPAddress & 0xff).append(".").append((IPAddress >> 8) & 0xff).append(".")
                .append((IPAddress >> 16) & 0xff).append(".").append((IPAddress >> 24) & 0xff).toString();
        String MACAddress = wifiinfo.getMacAddress();

        String msg = "Test WifiService!!!" + "\n"
                + "Invoke isWifiEnabled() method! -> Return: " + wifiEnabled + "\n"
                + "Invoke getWifiState() method! -> Return: " + wifiState + "\n"
                + "Invoke getSSID() method! -> Return: " + wifiName + "\n"
                + "Invoke getIpAddress() method! -> Return: " + IP + "\n"
                + "Invoke getMacAddress() method! -> Return: " + MACAddress;
        showDialog(context, msg);

        return true;
    }

    public static boolean callPackageManagerService(Context context) {
        PackageManager mPackageManager = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(applicationInfos, new ApplicationInfo.DisplayNameComparator(mPackageManager));
        StringBuilder msg = new StringBuilder("Test PackageManagerService!!!" + "\n" + "Invoke getInstalledApplications() method! APP info: " + "\n");
        for (ApplicationInfo applicationInfo : applicationInfos) {
            String appName = applicationInfo.loadLabel(mPackageManager).toString();
            String packageName = applicationInfo.packageName;
            msg.append("APP name: ").append(appName).append("; ").append("Package name: ").append(packageName).append("\n");
        }
        showDialog(context, msg.toString());

        return true;
    }

    public static boolean callAudioService(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        boolean microphoneMute = mAudioManager.isMicrophoneMute();
        int ringerMode = mAudioManager.getRingerMode();
        mAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.STREAM_MUSIC);
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        String msg = "Test AudioService!!!" + "\n"
                + "Invoke isMicrophoneMute() method! -> Return: " + microphoneMute + "\n"
                + "Invoke getRingerMode() method! -> Return: " + ringerMode + "\n"
                + "Invoke adjustVolume() method!" + "\n"
                + "Invoke getStreamVolume() method! -> Return: " + streamVolume;
        showDialog(context, msg);

        return true;
    }

    public static boolean callCameraService(Context context) {
        CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        String[] cameraIdList = null;
        try {
            cameraIdList = mCameraManager.getCameraIdList();
            CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(String.valueOf(0));

            String msg = "Test CameraService!!!" + "\n"
                    + "Invoke getCameraIdList() method! -> Return: " + Arrays.toString(cameraIdList) + "\n"
                    + "Invoke getCameraCharacteristics() method! -> Return: " + cameraCharacteristics;
            showDialog(context, msg);

            return true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean callBiometricService(Context context){
        BiometricManager mBiometricManager = (BiometricManager) context.getSystemService(Context.BIOMETRIC_SERVICE);
        int canAuth =  mBiometricManager.canAuthenticate();

        String msg = "Test BiometricService!!!" + "\n"
                + "Invoke canAuthenticate() method! -> Return: " + canAuth;
        showDialog(context, msg);
        
        return  true;
    }
    /*
     *API细化部分
     */
    public static boolean adjustAlarmVolume(Context context){
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
        return true;
    }
    public static boolean adjustMusicVoluem(Context context){
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
        return true;
    }
    public static boolean wifiEnable(Context context){
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiManager.setWifiEnabled(true);
        return true;
    }
    public static boolean addContent2Clipboard(Context context){
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null,"剪贴板中的数据");
        mClipboardManager.setPrimaryClip(clipData);
        return true;
    }
    public static boolean startCall(Context context){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        context.startActivity(intent);
        return true;
    }
    public static boolean startSend(Context context){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); //分享的是文本类型
        shareIntent.putExtra(Intent.EXTRA_TEXT, "文本内容");//分享出去的内容
//        context.startActivity(shareIntent);    //注意这里的变化

        context.startActivity(Intent.createChooser(shareIntent, "分享一下"));

        return true;
    }
}
