package pl.gorny.job.service;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;
import pl.gorny.job.AuctionEnderJob;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class AuctionEnderJobService {

    private Scheduler scheduler;
    private JobDetail job;
    private Trigger trigger;

    public void startAuctionEnderJon() {
        try {
            initializeAndStartScheduler();
            initializeJob();
            initializeAndStartTrigger();
            startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void initializeAndStartScheduler() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
    }

    private void initializeJob() {
        job = newJob(AuctionEnderJob.class)
                .withIdentity("auctionEnder", "group1")
                .build();
    }

    private void initializeAndStartTrigger() {
        trigger = newTrigger()
                .withIdentity("auctionEnderTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();
    }

    private void startJob() throws SchedulerException {
        scheduler.scheduleJob(job, trigger);
    }

}
