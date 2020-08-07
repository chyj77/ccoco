package cn.ccoco.platform.job.controller;

import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.controller.BaseController;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.job.entity.JobLog;
import cn.ccoco.platform.job.service.IJobLogService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequestMapping("jobLog")
@RequiredArgsConstructor
public class JobLogController extends BaseController {

    private final IJobLogService jobLogService;

    @GetMapping
    @PreAuthorize("hasAuthority('job:log:view')")
    public CcocoResponse jobLogList(QueryRequest request, JobLog log) {
        Map<String, Object> dataTable = getDataTable(this.jobLogService.findJobLogs(request, log));
        return CcocoResponse.success(dataTable);
    }

    @GetMapping("delete/{jobIds}")
    @PreAuthorize("hasAuthority('job:log:delete')")
    @ControllerEndpoint(exceptionMessage = "删除调度日志失败")
    public CcocoResponse deleteJobLog(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        String[] ids = jobIds.split(StringPool.COMMA);
        this.jobLogService.deleteJobLogs(ids);
        return CcocoResponse.success();
    }
/*
    @GetMapping("excel")
    @PreAuthorize("hasAuthority('job:log:export')")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) {
        List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
        ExcelKit.$Export(JobLog.class, response).downXlsx(jobLogs, false);
    }
    
 */
}
