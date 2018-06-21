package cn.edu.hqu.cst.tlj.absoluteaudiorecorder;


import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

/**
 * Data : 2018/5/16
 *
 * @author : TLJ
 * @parameter :
 * @return :
 */
public class MainActivity extends AppCompatActivity {
    TextView textView0;
    TextView textView1;
    TextView textView2;
    Record record = new Record();
    Record2 record2 = new Record2();
    Button bn;
    boolean bn_state = true;
    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bn = (Button) findViewById(R.id.bn);
        textView0 = (TextView) findViewById(R.id.txt0);
        textView1 = (TextView) findViewById(R.id.txt1);
        textView2 = (TextView) findViewById(R.id.txt2);
        final VibratorUtil vibratorUtil = new VibratorUtil(this);
        final ShakeListener shakeListener = new ShakeListener(this);
        final PhoneListener phoneListener = new PhoneListener(this);
        /**
         * 获取相应的权限
         */
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.RECORD_AUDIO, "麦克风", R.drawable.permission_ic_micro_phone));
        permissonItems.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE, "电话", R.drawable.permission_ic_phone));
        permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间", R.drawable.permission_ic_storage));
        permissonItems.add(new PermissionItem(Manifest.permission.VIBRATE, "震动", R.drawable.permission_ic_sensors));
        HiPermission.create(MainActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                        showToast("用户关闭权限申请");
                    }

                    @Override
                    public void onFinish() {
                        showToast("所有权限申请完成");
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
        /**
         * 监听器
         */
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                if (!record.status) {
                    record.StatrRecord(textView1);
                    vibratorUtil.Vibrate(new long[]{100, 100, 10, 100});
                } else
                    record.EndRecord(textView1);
            }
        });

        phoneListener.setOnPhoneListener(new PhoneListener.OnPhoneListener() {
            @Override
            public void onCall() {
                if (!record2.status) {
                    record2.StatrRecord(textView2);
                    vibratorUtil.Vibrate(new long[]{100, 100, 10, 100});
                } else
                    record2.EndRecord(textView2);
            }
        });

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bn_state) {
                    textView0.setText("已启动");
                    bn.setText("结束监听");
                    bn_state = false;
                    shakeListener.start(textView1);
                    phoneListener.start(textView2);

                } else {
                    textView0.setText("未启动");
                    bn.setText("开始监听");
                    bn_state = true;
                    //关闭正在进行的录音
                    if (record.status) {
                        record.EndRecord(textView1);
                    }
                    if (record2.status) {
                        record2.EndRecord(textView2);
                    }
                    //关闭监听
                    shakeListener.stop(textView1);
                    phoneListener.stop(textView2);
                }
            }
        });
    }
}
