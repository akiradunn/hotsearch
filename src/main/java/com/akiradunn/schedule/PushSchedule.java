package com.akiradunn.schedule;

import com.akiradunn.config.MailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PushSchedule {

    @Autowired
    private WeiboHotSearchTask weiboHotSearchTask;

    @Autowired
    private MailConfig mailConfig;

    @Scheduled(cron = "0 0/2 0-23 * * ?")
    public void calculate1() {
        log.info("热搜邮件定时任务执行开始！");

        weiboHotSearchTask.execute(mailConfig);

        log.info("热搜邮件定时任务执行结束！");
    }
}
