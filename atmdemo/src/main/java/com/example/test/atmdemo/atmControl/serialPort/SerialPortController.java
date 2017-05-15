package com.example.test.atmdemo.atmControl.serialPort;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

/**
 * Created by songjiancheng on 2016/4/11.
 */
public class SerialPortController {
    private static final String TAG = "SerialPortController";

    private static SerialPortController m_Instance = null;

    public static final int SERIAL_PORT_RECEIVE_DATA        = 50;
    public static final int SERIAL_PORT_CONFIG_ERROR        = 51;
    public static final int SERIAL_PORT_SECURITY_ERROR      = 52;
    public static final int SERIAL_PORT_UNKNOWN_ERROR       = 53;

    public static final int SERIAL_PORT_RECEIVE_DATA_NEW        = 56;

    public static final int SERIAL_PORT_WRITE_DATA_STR            = 57;
    public static final int SERIAL_PORT_WRITE_DATA_NEW_STR       = 58;
    public static final int SERIAL_PORT_WRITE_DATA_BYTES            = 59;
    public static final int SERIAL_PORT_WRITE_DATA_NEW_BYTES       = 60;

    private static final int WRITE_DATA_TIME       = 300;

    private volatile int m_iTimeCount = 0;
    private volatile int m_iTimeCountNew = 0;
    private SerialPort m_SerialPort = null;
    private SerialPortFinder m_SerialPortFinder = null;
    private InputStream m_InputStream = null;
    private OutputStream m_OutputStream = null;
//    private ReadThread m_ReadThread = null;

    private SerialPort m_SerialPortNew = null;
    private InputStream m_InputStreamNew = null;
    private OutputStream m_OutputStreamNew = null;
    private ReadThreadNew m_ReadThreadNew = null;

//    private Context mContext = null;

    public static synchronized SerialPortController getInstance() {
        if (null == m_Instance) {
            m_Instance = new SerialPortController();
        }
        return m_Instance;
    }

    public SerialPortController() {
        m_SerialPortFinder = new SerialPortFinder();
    }

//    public void init(Context context) {
//        mContext = context;
//    }

    private long lastClickTime;
    public long getTimeBetween() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD;
    }

    private long lastClickTimeNew;
    public long getTimeBetweenNew() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTimeNew;
        lastClickTimeNew = time;
        return timeD;
    }

//    /**
//     * 得到串口
//     */
//    public SerialPort getSerialPort(String devicekey, String baudratekey)
//            throws SecurityException, IOException, InvalidParameterException {
//        if (null == m_SerialPort) {
//			/* Read serial port parameters */
//            SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
//            String path = sp.getString(devicekey, "");
//            int baudrate = Integer.decode(sp.getString(baudratekey, "-1"));
//			/* Check parameters */
//            if ((path.length() == 0) || (baudrate == -1)) {
//                throw new InvalidParameterException();
//            }
//
//			/* Open the serial port */
//            m_SerialPort = new SerialPort(new File(path), baudrate, 0);
//            // mSerialPort = new SerialPort(new File("/dev/ttyS0"), baudrate,0);
//        }
//        return m_SerialPort;
//    }
//
//    /**
//     * 得到串口
//     */
//    public SerialPort getSerialPortNew(String devicekey, String baudratekey)
//            throws SecurityException, IOException, InvalidParameterException {
//        if (null == m_SerialPortNew) {
//            /* Read serial port parameters */
//            SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
//            String path = sp.getString(devicekey, "");
//            int baudrate = Integer.decode(sp.getString(baudratekey, "-1"));
//			/* Check parameters */
//            if ((path.length() == 0) || (baudrate == -1)) {
//                throw new InvalidParameterException();
//            }
//            /* Open the serial port */
//            m_SerialPortNew = new SerialPort(new File(path), baudrate, 0);
//            // mSerialPort = new SerialPort(new File("/dev/ttyS0"), baudrate,0);
//        }
//
//        return m_SerialPortNew;
//    }

//    /**
//     * 得到串口
//     */
//    public SerialPort getSerialPort(String dev,int baudrate) throws SecurityException, IOException, InvalidParameterException {
//        if (m_SerialPort == null) {
//
//			/* Open the serial port */
//            m_SerialPort = new SerialPort(new File(dev), baudrate,0);
//        }
//        return m_SerialPort;
//    }

    /**
     * 得到串口
     */
    public SerialPort getSerialPortNew(String dev,int baudrate) throws SecurityException, IOException, InvalidParameterException {
        if (m_SerialPortNew == null) {
            /* Open the serial port */
            m_SerialPortNew = new SerialPort(new File(dev), baudrate,0);
        }

        return m_SerialPortNew;
    }

    public SerialPortFinder getSerialPortFinder() {
        return m_SerialPortFinder;
    }

//    /**
//     * 打开并监视串口
//     */
//    public void openSerialPort(String devicekey, String baudratekey) {
//
//        try {
//            //m_SerialPort = m_application.getSerialPort("/dev/ttyS1", 19200);//（写死串口）
//            m_SerialPort = getSerialPort(devicekey, baudratekey);
//
//            m_OutputStream = m_SerialPort.getOutputStream();
//            m_InputStream = m_SerialPort.getInputStream();
//
//			/* Create a receiving thread */
//            if (null != m_ReadThread) {
//                m_ReadThread.interrupt();
//                m_ReadThread = null;
//            }
//            m_ReadThread = new ReadThread();
//            m_ReadThread.setName("ReadThread");
//            m_ReadThread.setRun(true);
//            m_ReadThread.setPriority(Thread.MAX_PRIORITY);
//            m_ReadThread.start();
//        } catch (SecurityException e) {
//            if (m_ReceiveHandler != null) {
//                m_ReceiveHandler.sendEmptyMessage(SERIAL_PORT_SECURITY_ERROR);
//            }
//        } catch (IOException e) {
//            if (m_ReceiveHandler != null) {
//                m_ReceiveHandler.sendEmptyMessage(SERIAL_PORT_UNKNOWN_ERROR);
//            }
//        } catch (InvalidParameterException e) {
//            if (m_ReceiveHandler != null) {
//                m_ReceiveHandler.sendEmptyMessage(SERIAL_PORT_CONFIG_ERROR);
//            }
//        }
//    }
//
//    /**
//     * 打开并监视串口
//     */
//    public void openSerialPortNew(String devicekey, String baudratekey) {
//
//        try {
//            //m_SerialPort = m_application.getSerialPort("/dev/ttyS1", 19200);//（写死串口）
//            m_SerialPortNew = getSerialPortNew(devicekey, baudratekey);
//
//            m_OutputStreamNew = m_SerialPortNew.getOutputStream();
//            m_InputStreamNew = m_SerialPortNew.getInputStream();
//
//            if (null != m_ReadThreadNew) {
//                m_ReadThreadNew.interrupt();
//                m_ReadThreadNew = null;
//            }
//            m_ReadThreadNew = new ReadThreadNew();
//            m_ReadThreadNew.setName("ReadThreadNew");
//            m_ReadThreadNew.setRun(true);
//            m_ReadThreadNew.setPriority(Thread.MAX_PRIORITY);
//            m_ReadThreadNew.start();
//
//        } catch (SecurityException e) {
//
//        } catch (IOException e) {
//
//        } catch (InvalidParameterException e) {
//
//        }
//    }

//    /**
//     * 打开并监视串口
//     */
//    public void openSerialPort(String dev,int baudrate) {
//
//        try {
//            //m_SerialPort = getSerialPort("/dev/ttyS1", 19200);//（写死串口）
//            m_SerialPort = getSerialPort(dev, baudrate);
//            m_OutputStream = m_SerialPort.getOutputStream();
//            m_InputStream = m_SerialPort.getInputStream();
//
//			/* Create a receiving thread */
//            if (null != m_ReadThread) {
//                m_ReadThread.interrupt();
//                m_ReadThread = null;
//            }
//            m_ReadThread = new ReadThread();
//            m_ReadThread.setName("ReadThread");
//            m_ReadThread.setRun(true);
//            m_ReadThread.setPriority(Thread.NORM_PRIORITY + 3);
//            m_ReadThread.start();
//        } catch (SecurityException e) {
//            if (m_ReceiveHandler != null) {
//                m_ReceiveHandler.sendEmptyMessage(SERIAL_PORT_SECURITY_ERROR);
//            }
//        } catch (IOException e) {
//            if (m_ReceiveHandler != null) {
//                m_ReceiveHandler.sendEmptyMessage(SERIAL_PORT_UNKNOWN_ERROR);
//            }
//        } catch (InvalidParameterException e) {
//            if (m_ReceiveHandler != null) {
//                m_ReceiveHandler.sendEmptyMessage(SERIAL_PORT_CONFIG_ERROR);
//            }
//        }
//    }

    /**
     * 打开并监视串口
     */
    public void openSerialPortNew(String dev,int baudrate) {
        try {
            //m_SerialPort = getSerialPort("/dev/ttyS1", 19200);//（写死串口）
            m_SerialPortNew = getSerialPortNew(dev, baudrate);
            m_OutputStreamNew = m_SerialPortNew.getOutputStream();
            m_InputStreamNew = m_SerialPortNew.getInputStream();

			/* Create a receiving thread */
            if (null != m_ReadThreadNew) {
                m_ReadThreadNew.interrupt();
                m_ReadThreadNew = null;
            }
            m_ReadThreadNew = new ReadThreadNew();
            m_ReadThreadNew.setName("ReadThreadNew");
            m_ReadThreadNew.setRun(true);
            m_ReadThreadNew.setPriority(Thread.NORM_PRIORITY + 3);
            m_ReadThreadNew.start();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 关闭串口
//     */
//    public void closeSerialPort() {
//        if (m_OutputStream != null) {
//            try {
//                m_OutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            m_OutputStream = null;
//        }
//
//        if (m_InputStream != null) {
//            try {
//                m_InputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            m_InputStream = null;
//        }
//
//        if (m_SerialPort != null) {
//            m_SerialPort.close();
//            m_SerialPort.closeStream();
//            m_SerialPort = null;
//        }
//    }

    /**
     * 关闭串口
     */
    public void closeSerialPortNew() {
        if (m_OutputStreamNew != null) {
            try {
                m_OutputStreamNew.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            m_OutputStreamNew = null;
        }

        if (m_InputStreamNew != null) {
            try {
                m_InputStreamNew.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            m_InputStreamNew = null;
        }
        if (m_SerialPortNew != null) {
            m_SerialPortNew.close();
            m_SerialPortNew.closeStream();
            m_SerialPortNew = null;
        }
    }

//    public void writeData(String str) {
//        if(null == str) {
//            Log.e(TAG, "writeData str is null");
//            return;
//        }
//        try {
//            if (null != m_OutputStream) {
//                if ((getTimeBetween() >= WRITE_DATA_TIME) || (m_iTimeCount > 1)) {
//                    m_iTimeCount = 0;
//                    m_OutputStream.write(str.getBytes());
//                    m_OutputStream.flush();
//                    Log.d(TAG, "writeData 向串口写数据  str: " + str);
//                } else {
//                    if (m_ReceiveHandler != null) {
//                        m_iTimeCount++;
//                        Message message = m_ReceiveHandler.obtainMessage();
//                        message.what = SERIAL_PORT_WRITE_DATA_STR;
//                        message.obj = str;
//                        m_ReceiveHandler.sendMessageDelayed(message,WRITE_DATA_TIME*m_iTimeCount);
//                    }
//                }
//
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "writeData 向串口写数据错误 e：" + e + " str: " + str);
//        }
//    }

    public void writeDataImmediately(String data) {
        try {
            m_OutputStream.write(data.getBytes());
            m_OutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataNew(String str) {
        if(null == str) {
            Log.e(TAG, "writeDataNew str is null");
            return;
        }
        Log.d(TAG, "writeDataNew 向串口写数据  str: " + str);
        try {
            if (null != m_OutputStreamNew) {
//                if (getTimeBetweenNew() >= WRITE_DATA_TIME) {
                m_iTimeCountNew = 0;
                m_OutputStreamNew.write(str.getBytes());
                m_OutputStreamNew.flush();
//                }
//                else {
//                    if (m_ReceiveHandlerNew != null) {
//                        m_iTimeCountNew++;
//                        Message message = m_ReceiveHandlerNew.obtainMessage();
//                        message.what = SERIAL_PORT_WRITE_DATA_NEW_STR;
//                        message.obj = str;
//                        m_ReceiveHandlerNew.sendMessageDelayed(message,WRITE_DATA_TIME*m_iTimeCountNew);
//                    }
//                }

            }
        } catch (IOException e) {
            Log.e(TAG, "writeDataNew 向串口写数据错误 e：" + e + " str: " + str);
        }
    }

    public void writeDataImmediately(byte[] bytes) {
        try {
            m_OutputStream.write(bytes);
            m_OutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void writeData(byte[] bytes) {
//        if(null == bytes) {
//            Log.e(TAG, "writeData bytes is null");
//            return;
//        }
//        try {
//            if (null != m_OutputStream) {
//                if ((getTimeBetween() >= WRITE_DATA_TIME) || (m_iTimeCount > 1)) {
//                    m_iTimeCount = 0;
//                    m_OutputStream.write(bytes);
//                    m_OutputStream.flush();
//                } else {
//                    if (m_ReceiveHandler != null) {
//                        m_iTimeCount++;
//                        Message message = m_ReceiveHandler.obtainMessage();
//                        message.what = SERIAL_PORT_WRITE_DATA_BYTES;
//                        message.obj = bytes;
//                        m_ReceiveHandler.sendMessageDelayed(message,WRITE_DATA_TIME*m_iTimeCount);
//                    }
//                }
//
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "writeData 向串口写数据错误 e：" + e);
//        }
//    }

    public void writeDataNew(byte[] bytes) {
        if(null == bytes) {
            Log.e(TAG, "writeDataNew bytes is null");
            return;
        }
        try {
            if (null != m_OutputStreamNew) {
                m_iTimeCountNew = 0;
                m_OutputStreamNew.write(bytes);
                m_OutputStreamNew.flush();
//                else {
//                    if (m_ReceiveHandlerNew != null) {
//                        m_iTimeCountNew++;
//                        Message message = m_ReceiveHandlerNew.obtainMessage();
//                        message.what = SERIAL_PORT_WRITE_DATA_NEW_BYTES;
//                        message.obj = bytes;
//                        m_ReceiveHandlerNew.sendMessageDelayed(message,WRITE_DATA_TIME*m_iTimeCountNew);
//                    }
//                }

            }
        } catch (IOException e) {
            Log.e(TAG, "writeDataNew 向串口写数据错误 e：" + e);
        }
    }

//    private Handler m_ReceiveHandler = null;
//    public void setHandler(Handler receiveHandler) {
//        m_ReceiveHandler = receiveHandler;
//    }
//
//    private Handler m_ReceiveHandlerNew = null;
//    public void setHandlerNew(Handler receiveHandlerNew) {
//        m_ReceiveHandlerNew = receiveHandlerNew;
//    }

    private OnReceivedDataListener onReceivedDataListener;
    public void setOnReceivedDataListener(OnReceivedDataListener onReceivedDataListener){
        this.onReceivedDataListener = onReceivedDataListener;
    }


//    /**
//     * 监视串口得到串口发送的数据
//     *
//     * @author Administrator
//     *
//     */
//    private class ReadThread extends Thread {
//
//        private boolean bIsRun;
//
//        public boolean getRun() {
//            return bIsRun;
//        }
//
//        public void setRun(boolean bRun) {
//            this.bIsRun = bRun;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//
//            if (null == m_InputStream) {
//                Log.e(TAG, "readthread m_InputStream is null");
//                return;
//            }
//            Log.d(TAG, "进入readthread啦------getRun(): " + getRun());
//            byte[] buffer = null;
//           // String read = "";
//            int byteCount = 0;
//            while (bIsRun) {
//
//                try {
//                    buffer = new byte[512];
//                    byteCount = m_InputStream.read(buffer);
//                    /*if (byteCount > 0) {
//                       // read = new String(buffer, 0, byteCount);
//                        m_bIsSendData = true;
//                    } else {
//               //         read = "";
//                    }*/
//
//                   // TcnVendIF.getInstance().LoggerDebug(TAG, "read: " + read);
//                    if (byteCount > 0) {
//                        if (m_ReceiveHandler != null) {
//                            Message message = m_ReceiveHandler.obtainMessage();
//                            message.what = SERIAL_PORT_RECEIVE_DATA;
//                            message.arg1 = byteCount;
//                            message.obj = buffer;
//                            m_ReceiveHandler.sendMessage(message);  //发送到另一个线程处理 read = new String(buffer, 0, byteCount);
//                        } else {
//                            Log.e(TAG, "m_ReceiveHandler is null" );
//                        }
//                    }
//
//                } catch (IOException e) {
//                    Log.e(TAG, "ReadThread IOException e: " + e);
//                    e.printStackTrace();
//                    break;
//                }
//            }
//        }
//    }

    /**
     * 监视串口得到串口发送的数据
     *
     * @author Administrator
     *
     */
    private class ReadThreadNew extends Thread {

        private boolean bIsRun;

        public boolean getRun() {
            return bIsRun;
        }

        public void setRun(boolean bRun) {
            this.bIsRun = bRun;
        }

        @Override
        public void run() {
            super.run();

            if (null == m_InputStreamNew) {
                Log.e(TAG, "ReadThreadNew m_InputStreamNew is null");
                return;
            }
            Log.d(TAG, "进入ReadThreadNew啦------getRun(): " + getRun());
            while (bIsRun) {
                try {
                    byte[] buffer = null;
                    String readNew = "";
                    int byteCount = 0;
                    buffer = new byte[512];
                    byteCount = m_InputStreamNew.read(buffer);
                   // TcnVendIF.getInstance().LoggerDebug(TAG, "readNew: " + readNew);
                    if (byteCount > 0) {
                        readNew = new String(buffer, 0, byteCount);
                        Log.d("SerialPort", readNew + "-" + byteCount);

//                        if (m_ReceiveHandlerNew != null && command != null) {
//                            Message message = m_ReceiveHandlerNew.obtainMessage();
//                            message.what = SERIAL_PORT_RECEIVE_DATA_NEW;
//                            message.arg1 = byteCount;
//                            message.obj = command;
//                            m_ReceiveHandlerNew.sendMessage(message);
//                        }

                        if (onReceivedDataListener != null){
                            onReceivedDataListener.onReceived(readNew);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "ReadThreadNew IOException e: " + e);
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public interface OnReceivedDataListener{
        void onReceived(String data);
    }

}
