package com.example.test.freeMind.dao;

import android.content.Context;
import android.provider.Settings;

import com.activeandroid.query.Select;
import com.example.test.commonlibrary.utils.CommonUtil;
import com.example.test.freeMind.GlobalUtils;
import com.example.test.freeMind.db.MindNodeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijie on 2017/6/7.
 */

public class MindNodeDao {
    /**
     * 插入数据
     * @param content
     * @param parentNodeId
     * @param context
     */
    public static void insert(String content, String parentNodeId, String rootNodeId,Context context){
        MindNodeModel mindNodeModel = new MindNodeModel();
        mindNodeModel.content = content;
        mindNodeModel.parentNodeId = parentNodeId;
        mindNodeModel.rootNodeId = rootNodeId;
        mindNodeModel.mindNodeId = GlobalUtils.createDBId("MindNode", CommonUtil.getDeviceId(context));
        mindNodeModel.createTime = System.currentTimeMillis();
        mindNodeModel.updateTime = System.currentTimeMillis();
        mindNodeModel.save();
    }

    /**
     * 更新节点
     * @param mindNodeModel
     */
    public static void update(MindNodeModel mindNodeModel){
        mindNodeModel.updateTime = System.currentTimeMillis();
        mindNodeModel.save();
    }

    /**
     * 获取所有根节点
     * @return
     */
    public static List<MindNodeModel> getAllRootNode(){
        return new Select()
                .from(MindNodeModel.class)
                .where("parent_node_id=?", 0)
                .execute();
    }

    /**
     * 根据父节点获取子节点
     * @param parentNodeId
     * @return
     */
    public static List<MindNodeModel> getChildNode(String parentNodeId){
        return new Select()
                .from(MindNodeModel.class)
                .where("parent_node_id=?", parentNodeId)
                .execute();
    }

    /**
     * 获取父节点
     * @param mindNodeModel
     * @return
     */
    public static MindNodeModel getParentNode(MindNodeModel mindNodeModel){
        return new Select()
                .from(MindNodeModel.class)
                .where("mind_node_id=?", mindNodeModel.parentNodeId)
                .executeSingle();
    }

    /**
     * 根据节点id获取父节点
     * @param nodeId
     * @return
     */
    public static MindNodeModel getNodeByNodeId(String nodeId){
        return new Select()
                .from(MindNodeModel.class)
                .where("mind_node_id=?", nodeId)
                .executeSingle();
    }

    /**
     * 获取叶子节点
     * @param rootNodeId
     * @return
     */
    public static List<MindNodeModel> getleafNode(String rootNodeId){
        List<MindNodeModel> nodeModelList = new Select()
                .from(MindNodeModel.class)
                .where("root_node_id=?", rootNodeId)
                .execute();

        List<MindNodeModel> leafList = new ArrayList<>();
        for (MindNodeModel mindNodeModel : nodeModelList){
            if (!new Select()
                    .from(MindNodeModel.class)
                    .where("parent_node_id=?", mindNodeModel.mindNodeId).exists()){
                leafList.add(mindNodeModel);
            }
        }

        return leafList;
    }
}
