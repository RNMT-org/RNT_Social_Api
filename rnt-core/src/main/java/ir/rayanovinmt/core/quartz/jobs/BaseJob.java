package ir.rayanovinmt.core.quartz.jobs;

import ir.rayanovinmt.core.context.SpringContext;
import ir.rayanovinmt.core.quartz.entity.JobExecutionEntity;
import ir.rayanovinmt.core.quartz.entity.JobStatus;
import ir.rayanovinmt.core.quartz.service.JobExecutionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

public abstract class BaseJob implements Job {

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobExecutionService jobExecutionService = SpringContext.getBean(JobExecutionService.class);

        String jobName = context.getJobDetail().getKey().getName();
        String jobGroup = context.getJobDetail().getKey().getGroup();
        String ip = context.getMergedJobDataMap().getString("ip");
        String username = context.getMergedJobDataMap().getString("username");
        String serverIp = context.getMergedJobDataMap().getString("serverIp");
        String jobParameters = context.getMergedJobDataMap().getString("jobParameters");

        JobExecutionEntity jobExecution = jobExecutionService.createJobEntry(jobName, jobGroup, ip, serverIp, username, jobParameters);
        Long jobId = jobExecution.getId();

        try {
            jobExecutionService.updateJobStatus(jobId, JobStatus.RUNNING, null, null);
            executeJob(context);
            jobExecutionService.updateJobStatus(jobId, JobStatus.SUCCESS, null, null);
        } catch (Exception e) {
            jobExecutionService.updateJobStatus(jobId, JobStatus.FAILED, e.getMessage(), Arrays.toString(e.getStackTrace()));
            throw new JobExecutionException(e);
        }
    }



    public abstract void executeJob(JobExecutionContext context) throws Exception;
}