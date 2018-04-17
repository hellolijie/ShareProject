package com.example.test.freeMind.db.dao;

import android.content.Context;

import com.activeandroid.query.Select;
import com.example.test.commonlibrary.utils.CommonUtil;
import com.example.test.freeMind.GlobalUtils;
import com.example.test.freeMind.db.model.MindNodeModel;
import com.example.test.freeMind.db.model.TaskModel;

import java.util.List;

/**
 * Created by lijie on 2017/6/7.
 */

public class TaskDao {
    /**
     * 插入
     * @param mindNodeId
     * @param consumption
     * @param context
     */
    public static void insert(String mindNodeId, long consumption, Context context){
        TaskModel taskModel = new TaskModel();
        taskModel.taskId = GlobalUtils.createDBId("TaskModel", CommonUtil.getDeviceId(context));
        taskModel.mindNodeId = mindNodeId;
        taskModel.consumption = consumption;
        taskModel.createTime = System.currentTimeMillis();
        taskModel.updateTime = System.currentTimeMillis();
        taskModel.save();
    }

    /**
     * 更新
     * @param taskModel
     */
    public static void update(TaskModel taskModel){
        taskModel.updateTime = System.currentTimeMillis();
        taskModel.save();
    }

    /**
     * 获取任务列表
     * @param mindRootId
     * @return
     */
    public static List<TaskModel> getTaskList(String mindRootId){
        return new Select()
                .from(TaskModel.class)
                .as("task")
                .innerJoin(MindNodeModel.class)
                .as("mindNode")
                .on("mindNode.root_node_id=?", mindRootId)
                .and("task.mind_node_id=mindNode.mind_node_id")
                .execute();
    }

    /**
     * 获取任务总时间
     * @param mindRootId
     * @return
     */
    public static long measureTaskConsumption(String mindRootId){
        List<TaskModel> taskModelList = getTaskList(mindRootId);
        long totalConsumption = 0;
        for (TaskModel taskModel : taskModelList){
            totalConsumption += taskModel.consumption;
        }
        return totalConsumption;
    }
}
