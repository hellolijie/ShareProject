package cn.huna.jerry.nettywrapperlibrary.udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by lijie on 2017/7/17.
 */

public class UdpUtils {

    public static UdpServer createServer(){
        return new UdpServer();
    }

    /**
     * 发送广播
     * @param msg
     */

    public static void sendBroadCast(final String msg, int port){
        DatagramSocket sendSocket = null;
        try {
            sendSocket = new DatagramSocket();
            sendSocket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
        byte[] buf = new byte[0];
        try {
            buf = msg.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 创建发送类型的数据报：
        DatagramPacket sendPacket = null;
        try {
            sendPacket = new DatagramPacket(buf,buf.length, InetAddress.getByName("255.255.255.255"), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 通过套接字发送数据：
        try {
            sendSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sendSocket.close();
        }
    }

    /**
     * 发送普通udp包
     * @param ip
     * @param port
     * @param msg
     */
    public static void send(final InetAddress ip, final int port, final String msg){

        // 创建发送方的套接字，IP默认为本地，端口号随机
        DatagramSocket sendSocket = null;
        try {
            sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
        byte[] buf = new byte[0];
        try {
            buf = msg.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 创建发送类型的数据报：
        DatagramPacket sendPacket = null;
        sendPacket = new DatagramPacket(buf, buf.length, ip, port);

        // 通过套接字发送数据：
        try {
            sendSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sendSocket.close();
        }

    }
}
