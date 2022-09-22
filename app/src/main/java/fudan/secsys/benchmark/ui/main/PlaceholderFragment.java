package fudan.secsys.benchmark.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fudan.secsys.benchmark.R;
import fudan.secsys.benchmark.utils.EnvCheckUtils;

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
        View root = inflater.inflate(R.layout.fragment_envcheck, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        root.findViewById(R.id.checkRoot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Root："+EnvCheckUtils.checkFrida(), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.checkBuild).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Build："+EnvCheckUtils.checkFrida(), Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.checkFrida).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "FRIDA："+EnvCheckUtils.checkFrida(), Toast.LENGTH_SHORT).show();
            }
        });
        textView.setText("Hello from 环境检测");
        return root;
    }
    private  View createInvokeServiceView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_invokeservice, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        textView.setText("Hello from 系统服务");
        return root;
    }
}