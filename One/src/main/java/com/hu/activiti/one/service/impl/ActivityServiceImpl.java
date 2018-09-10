package com.hu.activiti.one.service.impl;

import com.hu.activiti.one.service.ActivityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ifly
 * @Date: 2018/9/10 18:36
 * @Description:
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Override
    public boolean startActivity() {
        logger.info("工作流启动....");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("apply", "zhangsan");
        map.put("approve", "lisi");
        // 流程启动
        ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("leave2", map);

        String processId = pi1.getId();
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("task 第一步:{}", task);
        // 完成第一步申请
        taskService.complete(task.getId(), map);

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("task 第二步:{}", task);
        String taskId2 = task.getId();
        map.put("pass", false);
        // 驳回申请
        taskService.complete(taskId2, map);

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("task 第三步:{}", task);

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        taskService.complete(task.getId(), map);

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("task 完成第二步申请:{}", task);
        String taskId3 = task.getId();
        map.put("pass", true);
        // 同意申请
        taskService.complete(taskId3, map);

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        logger.info("task 第四步:{}", task);
        logger.info("工作流结束....");
        return false;
    }
}
