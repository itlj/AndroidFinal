package cn.edu.hqu.cst.tlj.absoluteaudiorecorder;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

/**
 * Data : 2018/5/17
 *
 * @author : TLJ
 * @parameter :
 * @return :
 */
public class ShakeListener implements SensorEventListener {
    // 速度阈值，当摇晃速度达到这值后产生作用
    private static final int SPEED_SHRESHOLD = 800;
    // 两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 2000;
    // 传感器管理器
    private SensorManager sensorManager;
    // 传感器
    private Sensor sensor;
    // 重力感应监听器
    private OnShakeListener onShakeListener;
    // 上下文对象context
    private Context context;
    // 手机上一个位置时重力感应坐标
    private float lastX;
    private float lastY;
    private float lastZ;
    // 上次检测时间
    private long lastUpdateTime;

    // 构造器
    public ShakeListener(Context context) {
        // 获得监听对象
        this.context = context;
//        start();
    }

    // 开始
    public void start(TextView textView) {
        // 获得传感器管理器
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//        textView.setText("摇晃监听器:正在运行");
        if (sensorManager != null) {
            // 获得重力传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        // 注册
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // 停止检测
    public void stop(TextView textView) {
        System.out.println("摇晃监听结束");
        sensorManager.unregisterListener(this);
//        textView.setText("摇晃监听器:停止运行");
    }


    // 摇晃监听接口
    public interface OnShakeListener {
        void onShake();
    }

    // 设置重力感应监听器
    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        //获得x,y,z坐标
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        //获得x,y,z的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        //将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;

        double speed = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
        //达到速度阀值，判断时间间隔
        if (speed >= SPEED_SHRESHOLD) {
            //检测当前时间
            long currentUpdateTime = System.currentTimeMillis();
            //两次检测的时间间隔
            long timeInterval = currentUpdateTime - lastUpdateTime;
            //判断是否达到了检测时间间隔
            if (timeInterval < UPTATE_INTERVAL_TIME) {
                System.out.println("条件不符合 " + timeInterval + "ms");
                System.out.println("速度:" + speed + "m/s");
            } else {
                //现在的时间变成last时间
                lastUpdateTime = currentUpdateTime;
                System.out.println("条件符合 " + timeInterval + "ms");
                System.out.println("速度:" + speed + "m/s");
                onShakeListener.onShake();
            }
        }
    }
}
