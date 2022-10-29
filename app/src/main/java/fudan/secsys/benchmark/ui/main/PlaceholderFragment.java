package fudan.secsys.benchmark.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import fudan.secsys.benchmark.R;
import fudan.secsys.benchmark.utils.EnvCheckUtils;
import fudan.secsys.benchmark.utils.InvokeServiceUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int pageId;
    public PlaceholderFragment(int index) {
        pageId = index;
    }
    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment(index);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(pageId == 0) {
            return createEnvCheckView(inflater, container, savedInstanceState);
        }
        else{
            return createInvokeServiceView(inflater, container, savedInstanceState);
        }
    }
    private  View createEnvCheckView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_envcheck, container, false);
        final TextView rootView = root.findViewById(R.id.root_label);
        rootView.setText("Root: Unknown");
        final TextView fridaView = root.findViewById(R.id.frida_label);
        fridaView.setText("Frida: Unknown");
        final TextView buildView = root.findViewById(R.id.build_label);
        buildView.setText("Build: Unknown");
        final TextView magiskView = root.findViewById(R.id.magisk_label);
        magiskView.setText("Magisk: Unknown");
        final TextView emulatorView = root.findViewById(R.id.emulator_label);
        emulatorView.setText("Emulator: Unknown");
        final TextView selinuxView = root.findViewById(R.id.selinux_label);
        selinuxView.setText("Selinux: Unknown");
        root.findViewById(R.id.checkRoot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res=EnvCheckUtils.checkRoot();
                if(res){
                    rootView.setTextColor(getResources().getColor(R.color.red));
                }
                else{
                    rootView.setTextColor(getResources().getColor(R.color.green));
                }
                rootView.setText("Root: "+res);
            }
        });
        root.findViewById(R.id.checkBuild).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res=EnvCheckUtils.checkBuild();
                if(res.contains("test")||res.contains("debug")||res.contains("eng")){
                    buildView.setTextColor(getResources().getColor(R.color.red));
                }
                else{
                    buildView.setTextColor(getResources().getColor(R.color.green));
                }
                buildView.setText("Build: "+res);
            }
        });
        root.findViewById(R.id.checkFrida).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        v.setEnabled(true);
                    }
                };

                v.setEnabled(false);
                MyRunner.cnt=0;
                fridaView.setText("Frida: detecting...");
                ThreadPoolExecutor pool=new ThreadPoolExecutor(64,64,0, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(64));
                for(int i=0;i<64;i++){
                    MyRunner runner=new MyRunner(handler,fridaView,64,i*1024,(i+1)*1024);
                    pool.execute(runner);
                }
            }
        });
        root.findViewById(R.id.checkMagisk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res=EnvCheckUtils.checkMagisk();
                if(res){
                    magiskView.setTextColor(getResources().getColor(R.color.red));
                }
                else{
                    magiskView.setTextColor(getResources().getColor(R.color.green));
                }
                magiskView.setText("Magisk: "+res);
            }
        });
        root.findViewById(R.id.checkEmulator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res=EnvCheckUtils.checkEmulator(root.getContext());
                if(res){
                    emulatorView.setTextColor(getResources().getColor(R.color.red));
                }
                else{
                    emulatorView.setTextColor(getResources().getColor(R.color.green));
                }
                emulatorView.setText("Emulator: "+res);
            }
        });
        root.findViewById(R.id.checkSelinux).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res=EnvCheckUtils.checkSelinux();
                if(res){
                    selinuxView.setTextColor(getResources().getColor(R.color.green));
                    selinuxView.setText("Selinux: Enforcing");
                }
                else{
                    selinuxView.setTextColor(getResources().getColor(R.color.red));
                    selinuxView.setText("Selinux: Permissive");
                }

            }
        });
        return root;
    }
    private  View createInvokeServiceView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_invokeservice, container, false);

        root.findViewById(R.id.service1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service1 feedback:"+ InvokeServiceUtils.callVibratorService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service2 feedback:"+ InvokeServiceUtils.callClipboardService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service3 feedback:"+ InvokeServiceUtils.callPowerManagerService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service4).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service4 feedback:"+ InvokeServiceUtils.callActivityManagerService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service5 feedback:"+InvokeServiceUtils.callLocationManagerService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service6 feedback:"+InvokeServiceUtils.callWifiService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service7 feedback:"+InvokeServiceUtils.callPackageManagerService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service8 feedback:"+InvokeServiceUtils.callAudioService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service9 feedback:"+InvokeServiceUtils.callCameraService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.service10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service10 feedback:"+InvokeServiceUtils.callBiometricService(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.api1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service11 feedback:"+InvokeServiceUtils.adjustAlarmVolume(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.api2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service12 feedback:"+InvokeServiceUtils.adjustMusicVoluem(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.api3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service13 feedback:"+InvokeServiceUtils.startCall(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.api4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Service14 feedback:"+InvokeServiceUtils.startSend(view.getContext()), Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

}