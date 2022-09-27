package fudan.secsys.benchmark.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class InvokeServiceUtils {
    public static boolean callVibratorService(Context context) {
        Toast.makeText(context, "Test VibratorService!!!", Toast.LENGTH_SHORT).show();
        Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        final boolean ret = mVibrator.hasVibrator();
        Toast.makeText(context, "Invoke hasVibrator() method! Return: " + ret, Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mVibrator.vibrate(1000);
        Toast.makeText(context, "Invoke vibrate() method!", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*long[] inp = {0, 1000, 2000, 3000, 4000};
        mVibrator.vibrate(inp, 3);
        Toast.makeText(context, "Invoke vibrate() method!", Toast.LENGTH_LONG).show();*/

        return true;
    }

    public static boolean callClipboardService(Context context) {
        Toast.makeText(context, "Test ClipboardService!!!", Toast.LENGTH_SHORT).show();
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //int userid = Binder.getCallingUid();
        final boolean ret = mClipboardManager.hasPrimaryClip();
        Toast.makeText(context, "Invoke hasPrimaryClip() method! Return: " + ret, Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ClipData mClipData = mClipboardManager.getPrimaryClip();
        Toast.makeText(context, "Invoke getPrimaryClip() method! Return: " + mClipData, Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mClipboardManager.clearPrimaryClip();
        Toast.makeText(context, "Invoke clearPrimaryClip() method!", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean callPowerManagerService(Context context) {
        Toast.makeText(context, "Test PowerManagerService!!!", Toast.LENGTH_SHORT).show();
        PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        final boolean ret = mPowerManager.isScreenOn();
        Toast.makeText(context, "Invoke isScreenOn() method! Return: " + ret, Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final boolean ret2 = mPowerManager.isPowerSaveMode();
        Toast.makeText(context, "Invoke isPowerSaveMode() method! Return: " + ret2, Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean callActivityManagerService(Context context) {
        Toast.makeText(context, "Test ActivityManagerService!!!", Toast.LENGTH_SHORT).show();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final boolean ret = mActivityManager.isLowRamDevice();
        Toast.makeText(context, "Invoke isLowRamDevice() method! Return: " + ret, Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "Invoke getAppTasks() method! Return: " + mActivityManager.getAppTasks(), Toast.LENGTH_SHORT).show();

        Toast.makeText(context, "Invoke getDeviceConfigurationInfo() method! Return: " + mActivityManager.getDeviceConfigurationInfo(), Toast.LENGTH_SHORT).show();

        return true;
    }

    public static boolean callLocationManagerService(Context context) {
        Toast.makeText(context, "Test LocationManagerService!!!", Toast.LENGTH_SHORT).show();
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(context, "Invoke getAllProviders() method! Return: "+mLocationManager.getAllProviders(),Toast.LENGTH_SHORT).show();

            Toast.makeText(context, "Invoke isProviderEnabled() method! Return: "+mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER),Toast.LENGTH_SHORT).show();

            //Location mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Toast.makeText(context, "Invoke getLastKnownLocation() method! Return: "+mLocation, Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }


}
