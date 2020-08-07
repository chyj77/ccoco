package cn.ccoco.platform.system.controller;


import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.entity.DeptTree;
import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.common.exception.CcocoException;
import cn.ccoco.platform.system.entity.Dept;
import cn.ccoco.platform.system.service.IDeptService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author CCoco
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("dept")
public class DeptController {

    private final IDeptService deptService;

    @GetMapping("select/tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public List<DeptTree<Dept>> getDeptTree() throws CcocoException {
        return this.deptService.findDepts();
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public CcocoResponse getDeptTree(Dept dept) throws CcocoException {
        List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
        return new CcocoResponse().success().data(depts);
    }

    @PostMapping
    @RequiresPermissions("dept:add")
    @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    public CcocoResponse addDept(@Valid Dept dept) {
        this.deptService.createDept(dept);
        return new CcocoResponse().success();
    }

    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    @ControllerEndpoint(operation = "删除部门", exceptionMessage = "删除部门失败")
    public CcocoResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws CcocoException {
        String[] ids = deptIds.split(StringPool.COMMA);
        this.deptService.deleteDepts(ids);
        return new CcocoResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("dept:update")
    @ControllerEndpoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    public CcocoResponse updateDept(@Valid Dept dept) throws CcocoException {
        this.deptService.updateDept(dept);
        return new CcocoResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws CcocoException {
        List<Dept> depts = this.deptService.findDepts(dept, request);
        ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
    }
}
