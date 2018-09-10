package com.hu.activiti.one;

import com.hu.activiti.one.service.ActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OneApplicationTests {

    @Resource(name = "activityService")
    private ActivityService activityService;

    @Test
    public void leaveProcess() {
        activityService.startActivity();
    }

}
