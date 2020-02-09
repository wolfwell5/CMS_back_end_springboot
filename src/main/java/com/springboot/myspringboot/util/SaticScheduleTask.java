package com.springboot.myspringboot.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SaticScheduleTask implements SchedulingConfigurer {


    //    每分钟执行一次
//    https://www.cnblogs.com/liberty777/p/10741738.html
//    https://blog.csdn.net/longzhongxiaoniao/article/details/89344250
    @Scheduled(cron = "*/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=5000)
    private void configureTask() {
        System.out.println("执行静态定时任务时间: " + LocalDateTime.now());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
//                    String cron = cronMapper.getCron();
                    String cron = "*/5 * * * * ?";

                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
//    @Scheduled(cron = "*/2 * * * * ?")
//    private void configureTask2() {
//        System.out.println("configureTask2: " + LocalDateTime.now());
//    }

}
