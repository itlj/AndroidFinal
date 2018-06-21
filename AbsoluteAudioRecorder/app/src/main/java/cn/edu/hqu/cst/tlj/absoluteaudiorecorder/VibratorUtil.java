package cn.edu.hqu.cst.tlj.absoluteaudiorecorder;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

public class VibratorUtil {
    private Context context;

    public VibratorUtil(Context context) {
        this.context = context;
    }

    public void Vibrate(long[] pattern) {
        System.out.println("震动............");
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, -1);
    }
}
