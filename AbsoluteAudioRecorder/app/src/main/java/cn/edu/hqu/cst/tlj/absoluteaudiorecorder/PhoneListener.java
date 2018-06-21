package cn.edu.hqu.cst.tlj.absoluteaudiorecorder;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * Data : 2018/5/26
 *
 * @author : TLJ
 * @parameter :
 * @return :
 */
public class PhoneListener extends PhoneStateListener {
    private TelephonyManager telephonyManager;
    private Context context;
    private OnPhoneListener onPhoneListener;
    private boolean BeCalled = false;

    public PhoneListener(Context context) {
        this.context = context;
//        start();
    }

    public interface OnPhoneListener {
        void onCall();
    }

    public void setOnPhoneListener(OnPhoneListener listener) {
        onPhoneListener = listener;
    }


    public void start(TextView textView) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
//            textView.setText("电话监听器:正在运行");
            System.out.println("电话监听器创建成功");
        } else {
            System.out.println("电话监听器创建失败");
        }
    }

    public void stop(TextView textView) {
        System.out.println("电话监听结束");
        telephonyManager.listen(this, PhoneStateListener.LISTEN_NONE);
//        textView.setText("电话监听器:停止运行");
    }

    @Override
    public void onCallStateChanged(int state, String number) {

        switch (state) {

            case TelephonyManager.CALL_STATE_RINGING: //电话响铃时
                System.out.println("检测到通话");
                onPhoneListener.onCall();
                BeCalled = true;
                break;

            case TelephonyManager.CALL_STATE_IDLE: //挂断电话时
                if (BeCalled) {
                    System.out.println("检测到挂断");
                    onPhoneListener.onCall();
                }
                break;

            default:
                break;

        }
        super.onCallStateChanged(state, number);
    }
}
