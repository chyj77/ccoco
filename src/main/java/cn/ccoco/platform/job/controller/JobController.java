package cn.ccoco.platform.job.controller;

import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.job.entity.Job;
import cn.ccoco.platform.job.service.IJobService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequestMapping("job")
@RequiredArgsConstructor
public class JobController extends BaseController {

    private final IJobService jobService;

    @GetMapping
    @PreAuthorize("hasAuthority('job:view')")
    public CcocoResponse jobList(QueryRequest request, Job job) {
        Map<String, Object> dataTable = getDataTable(this.jobService.findJobs(request, job));
        return CcocoResponse.success(dataTable);
    }

    @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('job:add')")
    @ControllerEndpoint(operation = "新增定时任务", exceptionMessage = "新增定时任务失败")
    public CcocoResponse addJob(@Valid Job job) {
        this.jobService.createJob(job);
        return CcocoResponse.success();
    }

    @GetMapping("delete/{jobIds}")
    @PreAuthorize("hasAuthority('job:delete')")
    @ControllerEndpoint(operation = "删除定时任务", exceptionMessage = "删除定时任务失败")
    public CcocoResponse deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        String[] ids = jobIds.split(StringPool.COMMA);
        this.jobService.deleteJobs(ids);
        return CcocoResponse.success();
    }

    @PostMapping("update")
    @ControllerEndpoint(operation = "修改定时任务", exceptionMessage = "修改定时任务失败")
    public CcocoResponse updateJob(@Valid Job job) {
        this.jobService.updateJob(job);
        return CcocoResponse.success();
    }

    @GetMapping("run/{jobIds}")
    @PreAuthorize("hasAuthority('job:run')")
    @ControllerEndpoint(operation = "执行定时任务", exceptionMessage = "执行定时任务失败")
    public CcocoResponse runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.run(jobIds);
        return CcocoResponse.success();
    }

    @GetMapping("pause/{jobIds}")
    @PreAuthorize("hasAuthority('job:pause')")
    @ControllerEndpoint(operation = "暂停定时任务", exceptionMessage = "暂停定时任务失败")
    public CcocoResponse pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.pause(jobIds);
        return CcocoResponse.success();
    }

    @GetMapping("resume/{jobIds}")
    @PreAuthorize("hasAuthority('job:resume')")
    @ControllerEndpoint(operation = "恢复定时任务", exceptionMessage = "恢复定时任务失败")
    public CcocoResponse resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.resume(jobIds);
        return CcocoResponse.success();
    }
/*
    @GetMapping("excel")
    @PreAuthorize("hasAuthority('job:export')")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, Job job, ServerWebExchange webExchange) {
        List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
        ExcelKit.$Export(Job.class, webExchange).downXlsx(jobs, false);
    }
 */
}
