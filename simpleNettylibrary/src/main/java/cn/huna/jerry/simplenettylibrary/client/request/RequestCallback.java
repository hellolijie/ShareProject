package cn.huna.jerry.simplenettylibrary.client.request;

import cn.huna.jerry.simplenettylibrary.model.ErrorModel;

/**
 * 请求回调
 */
public abstract class RequestCallback {
    public long requestCreateTime;      //请求创建时间
    public long timeOverMilliseconds;    //超时时间
    public String requestKey;           //请求唯一标识

    public abstract void onSuc(String msgContent);
    public abstract void onError(ErrorModel errorModel);
}
