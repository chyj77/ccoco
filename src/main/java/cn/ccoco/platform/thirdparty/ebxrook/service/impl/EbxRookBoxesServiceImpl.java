package cn.ccoco.platform.thirdparty.ebxrook.service.impl;

import cn.ccoco.platform.common.entity.CCocoConstant;
import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.system.service.IUserDataPermissionService;
import cn.ccoco.platform.thirdparty.ebxrook.api.EbxRookBoxesService;
import cn.ccoco.platform.thirdparty.ebxrook.api.EbxRookProjectInfoService;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxes;
import cn.ccoco.platform.thirdparty.ebxrook.mapper.EbxRookBoxesMapper;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookBoxesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 智慧式用电数据开放平台--项目信息 Service实现
 *
 * @author Ccoco
 * @date 2020-07-20 16:41:47
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EbxRookBoxesServiceImpl extends ServiceImpl<EbxRookBoxesMapper, EbxRookBoxes> implements IEbxRookBoxesService {

    @Autowired
    EbxRookBoxesService ebxRookBoxesService;
    private final IUserDataPermissionService userDataPermissionService;

    @Override
    public IPage<EbxRookBoxes> findEbxRookBoxess(QueryRequest request, EbxRookBoxes ebxRookBoxes, User user) {
        String deptIds = userDataPermissionService.findByUserId(String.valueOf(user.getUserId()));
        LambdaQueryWrapper<EbxRookBoxes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(EbxRookBoxes::getDeptId,Arrays.asList(deptIds.split(",")));
        Page<EbxRookBoxes> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<EbxRookBoxes> findEbxRookBoxess(EbxRookBoxes ebxRookBoxes) {
        LambdaQueryWrapper<EbxRookBoxes> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createEbxRookBoxes(EbxRookBoxes ebxRookBoxes) throws Exception {
        this.save(ebxRookBoxes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEbxRookBoxes(EbxRookBoxes ebxRookBoxes) {
        this.saveOrUpdate(ebxRookBoxes);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEbxRookBoxes(EbxRookBoxes ebxRookBoxes) {
        LambdaQueryWrapper<EbxRookBoxes> wrapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEbxRookBoxes(String projectCode) {
        LambdaQueryWrapper<EbxRookBoxes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EbxRookBoxes::getProjectCode, projectCode);
        this.remove(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Async(CCocoConstant.ASYNC_POOL)
    @Override
    public void asyncBoxes(String projectCodes, Long deptId, boolean async) throws Exception {
        if (async) {
            List<EbxRookBoxes> ebxRookBoxesList = ebxRookBoxesService.getBoxes(projectCodes, deptId);
            List<EbxRookBoxes> ebxRookBoxesList2 = new ArrayList<>();
            if (null != ebxRookBoxesList) {
                for(EbxRookBoxes ebxRookBoxes:ebxRookBoxesList){
                    Integer b = this.lambdaQuery()
                            .eq(EbxRookBoxes::getProjectCode,ebxRookBoxes.getProjectCode())
                            .eq(EbxRookBoxes::getMac,ebxRookBoxes.getMac())
                            .count();
                    if(b==null || b==0){
                        ebxRookBoxesList2.add(ebxRookBoxes);
                    }

                }
                this.saveOrUpdateBatch(ebxRookBoxesList2);
            }
        } else {
            if (StringUtils.isEmpty(projectCodes)) {
                throw new Exception("项目编码是空的");
            }
            if (null == deptId) {
                throw new Exception("部门是空的");
            }
            String[] projectcodess = projectCodes.split(",");
            this.lambdaUpdate().in(EbxRookBoxes::getProjectCode, Arrays.asList(projectcodess)).set(EbxRookBoxes::getDeptId, deptId).update();
        }
    }

    @Override
    public long findTotalBox(String deptIds) {
        LambdaQueryWrapper<EbxRookBoxes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(EbxRookBoxes::getDeptId,Arrays.asList(deptIds.split(",")));
        return this.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public long findOnlineBox(String deptIds) {
        LambdaQueryWrapper<EbxRookBoxes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(EbxRookBoxes::getDeptId,Arrays.asList(deptIds.split(",")))
                .eq(EbxRookBoxes::getOnline,1);
        return this.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public long findUnderlineBox(String deptIds) {
        LambdaQueryWrapper<EbxRookBoxes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(EbxRookBoxes::getDeptId,Arrays.asList(deptIds.split(",")))
        .lt(EbxRookBoxes::getOnline,1);
        return this.baseMapper.selectCount(queryWrapper);
    }
}
