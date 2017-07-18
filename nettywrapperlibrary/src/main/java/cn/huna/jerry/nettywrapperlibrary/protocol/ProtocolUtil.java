package cn.huna.jerry.nettywrapperlibrary.protocol;

import cn.huna.jerry.nettywrapperlibrary.Utils;
import cn.huna.jerry.nettywrapperlibrary.protocol.model.TransmissionModel;

/**
 * Created by lijie on 2017/7/13.
 */

public class ProtocolUtil {
    /**
     * 生成正常请求model
     * @param msg
     * @return
     */
    public static TransmissionModel createRequestModel(String msg){
        TransmissionModel transmissionModel = new TransmissionModel();
        transmissionModel.transmissionType = TransmissionModel.TYPE_REQUEST;
        transmissionModel.transmissionIdentification = createIdentification();
        transmissionModel.transmissionContent = msg;

        return transmissionModel;
    }

    /**
     * 生成正常请求返回model
     * @param transmissionModel
     * @return
     */
    public static TransmissionModel createBackRequestModel(TransmissionModel transmissionModel){
        TransmissionModel backTransmissionModel = new TransmissionModel();
        backTransmissionModel.transmissionIdentification = transmissionModel.transmissionIdentification;
        backTransmissionModel.transmissionType = TransmissionModel.TYPE_REQUEST;
        return backTransmissionModel;
    }

    /**
     * 生成推送model
     * @param msg
     * @return
     */
    public static TransmissionModel createPushRequestModel(String msg){
        TransmissionModel transmissionModel = new TransmissionModel();
        transmissionModel.transmissionType = TransmissionModel.TYPE_PUSH;
        transmissionModel.transmissionIdentification = createIdentification();
        transmissionModel.transmissionContent = msg;
        return transmissionModel;
    }

    /**
     * 生成推送返回model
     * @param transmissionModel
     * @return
     */
    public static TransmissionModel createPushBackRequestModel(TransmissionModel transmissionModel){
        TransmissionModel backTransmissionModel = new TransmissionModel();
        backTransmissionModel.transmissionIdentification = transmissionModel.transmissionIdentification;
        backTransmissionModel.transmissionType = TransmissionModel.TYPE_PUSH;
        return backTransmissionModel;
    }

    /**
     * 生成唯一标识符
     *
     * @return
     */
    private static String createIdentification() {
        return Utils.md5(System.currentTimeMillis() + "-" + Math.random());
    }
}
