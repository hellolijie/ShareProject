package cn.huna.jerry.simplenettylibrary.model;

/**
 * Created by lijie on 17/1/26.
 */

public class TransmissionModel {
    public final static int TYPE_PUSH = -1;                //推送
    public final static int TYPE_REQUEST = 1;              //请求

    public int transmissionType;                     //类型
    public String transmissionIdentification;        //请求唯一标识
    public String transmissionContent;               //请求内容
}
