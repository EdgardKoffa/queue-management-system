package com.nsglobal.queue.scheduler.job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchedulerTest {
private int i=0;
   // @Scheduled(fixedRate = 1000)
    public void test() {

        log.info("----->Scheduler is running..."+i++);

    }

}
