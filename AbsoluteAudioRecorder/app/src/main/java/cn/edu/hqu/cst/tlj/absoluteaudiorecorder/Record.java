package cn.edu.hqu.cst.tlj.absoluteaudiorecorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Vibrator;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.Date;

/**
 * Data : 2018/5/16
 *
 * @author : TLJ
 * @parameter :
 * @return :
 */
public class Record {
    File soundFile;
    MediaRecorder mediaRecorder;
    //标记当前的录音启动状态
    boolean status;
    Date time;
    String str_time;

    public Record() {
        this.status = false;
    }

    public void StatrRecord(TextView textView) {
        System.out.println("开始摇晃录音");
        status = true;
        time = new Date();
        str_time = time.toLocaleString();
        try {
            soundFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getCanonicalFile() + "/摇晃录音:" + str_time + ".amr");
            System.out.println("文件地址:" + soundFile);
            mediaRecorder = new MediaRecorder();
            //设置录音的声音来源
            System.out.println("设置录音的声音来源.....");
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置录制声音的输出格式(必须在设置声音编码格式之前设置)
            System.out.println("设置录制声音的输出格式(必须在设置声音编码格式之前设置).....");
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //设置声音编码格式
            System.out.println("设置设置声音编码格式.....");
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            System.out.println("设置AudioEncoder.....");
            mediaRecorder.setOutputFile(soundFile.getAbsolutePath());
            System.out.println("设置OutputFile.....");
            mediaRecorder.prepare();
            System.out.println("设置prepare.....");
            mediaRecorder.start();
            System.out.println("设置摇晃监听器:正在录音.....");
            textView.setText("摇晃监听器:正在录音");

        } catch (Exception e) {
            System.out.println("检测到异常.....");
            e.printStackTrace();
        }
    }

    public void EndRecord(TextView textView) {
        System.out.println("结束摇晃录音");
        status = false;
        if (soundFile != null && soundFile.exists()) {
            //停止录音
            mediaRecorder.stop();
            System.out.println("设置摇晃监听器:结束录音.....");
            textView.setText("摇晃监听器:结束录音");
            //释放资源
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
