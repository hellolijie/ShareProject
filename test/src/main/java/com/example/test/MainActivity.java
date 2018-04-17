//package com.example.test;
//
//import android.content.Context;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.test.commonlibrary.net.ApiCreator;
//import com.example.test.net.ApiWrapper;
//
//import cn.huna.jerry.simplenettylibrary.client.ConnectionStateListener;
//import cn.huna.jerry.simplenettylibrary.client.request.RequestCallback;
//import cn.huna.jerry.simplenettylibrary.client.SimpleNetty;
//import cn.huna.jerry.simplenettylibrary.client.TcpClient;
//import cn.huna.jerry.simplenettylibrary.model.ErrorModel;
//import cn.huna.jerry.simplenettylibrary.server.TcpServer;
//import retrofit2.Response;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;
//import rx.schedulers.Schedulers;
//
//public class MainActivity extends AppCompatActivity {
//    private TcpServer tcpServer;
//    private TcpClient tcpClient;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    public void getIP(View view){
//        try {
//            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            int i = wifiInfo.getIpAddress();
//
//            ((TextView)findViewById(R.id.tv_display)).append(int2ip(i) + "\n");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * 启动服务端
//     * @param view
//     */
//    public void startServer(View view){
//        if (tcpClient != null){
//            Toast.makeText(this, "已启用服务端", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(tcpServer != null){
//            tcpServer.shutdown();
//        }
//        tcpServer = new TcpServer();
//        tcpServer.setOnHandelReceivedData(new TcpServer.OnHandelReceivedData() {
//            @Override
//            public String onReceivedData(String msgContent) {
//                printText(msgContent);
//
//                return "收到数据：" + msgContent;
//            }
//        });
//
//        tcpServer.run(5678);
//    }
//
//    /**
//     * 连接服务端
//     * @param view
//     */
//    public void connectServer(View view){
//        if (tcpServer != null){
//            Toast.makeText(this, "已启用服务端", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (tcpClient != null){
//            tcpClient.shutdown();
//        }
//
//        String ip = ((TextView)findViewById(R.id.et_ip)).getText().toString();
//        int port = Integer.parseInt(((TextView)findViewById(R.id.et_port)).getText().toString());
//        tcpClient = SimpleNetty.getInstance().connect(ip, port, new ConnectionStateListener() {
//            @Override
//            public void onDisconnect() {
//                printText("断开连接");
//            }
//
//            @Override
//            public void onConnect() {
//                printText("连接成功");
//            }
//
//            @Override
//            public void onHeartBeatTimeOver() {
//                printText("超时");
//            }
//        });
//
//    }
//
//    /**
//     * 发送数据
//     * @param view
//     */
//    public void sendMsg(View view){
//        if (tcpClient == null)
//            return;
//
//        String msg = ((TextView)findViewById(R.id.et_msg)).getText().toString();
//        tcpClient.sendMsg(msg, new RequestCallback(){
//
//            @Override
//            public void onSuc(String msgContent) {
//                printText("调用成功：" + msgContent);
//            }
//
//            @Override
//            public void onError(ErrorModel errorModel) {
//                printText("调用失败：" + errorModel.errorCode + "-" + errorModel.errorMessage  );
//            }
//        });
//    }
//
//    /**
//     * retrofit测试
//     * @param view
//     */
//    public void retrofitTest(View view){
//        ApiWrapper apiWrapper = ApiCreator.create(ApiWrapper.class, "http://api.fanyi.baidu.com/");
//        apiWrapper
////                .testBaidu()
//                .baiduTranslate("apple", "en", "zh", "2015063000000001", "1435660288", "f89f9594663708c1605f3d736d01d2d4")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Response<String>>() {
//                                 @Override
//                                 public void call(Response<String> stringResponse) {
//                                     Toast.makeText(MainActivity.this, stringResponse.body(), Toast.LENGTH_LONG).show();
//                                 }
//                             },
//                        new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//    }
//
//    private void printText(final String text){
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ((TextView)findViewById(R.id.tv_display)).append(text + "\n");
//            }
//        });
//
//    }
//
//    /**
//     * 将ip的整数形式转换成ip形式
//     *
//     * @param ipInt
//     * @return
//     */
//    private String int2ip(int ipInt) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(ipInt & 0xFF).append(".");
//        sb.append((ipInt >> 8) & 0xFF).append(".");
//        sb.append((ipInt >> 16) & 0xFF).append(".");
//        sb.append((ipInt >> 24) & 0xFF);
//        return sb.toString();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (tcpClient != null)
//            tcpClient.shutdown();
//
//        if (tcpServer != null)
//            tcpServer.shutdown();
//
//        SimpleNetty.getInstance().shutDownAll();
//
////        android.os.Process.killProcess(android.os.Process.myPid());
//    }
//}
