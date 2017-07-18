package cn.huna.jerry.nettywrapperlibrary.tcp.client;

/**
 * 连接状态回调
 */
public interface ConnectionStateListener {
    void onDisconnect();
    void onConnect();
    void onHeartBeatTimeOver();
}
