package cn.huna.jerry.simplenettylibrary.model;

/**
 * Created by lijie on 17/1/26.
 */

public class ErrorModel {
    public static final int ERROR_TIME_OUT = 0;         //连接超时
    public static final int ERROR_CONNECT_DISCONNECT = 1;       //连接已断开

    public int errorCode;
    public String errorMessage;

    public static ErrorModel newModel(int errorCode, String errorMessage){
        ErrorModel errorModel = new ErrorModel();
        errorModel.errorCode = errorCode;
        errorModel.errorMessage = errorMessage;
        return errorModel;
    }
}
