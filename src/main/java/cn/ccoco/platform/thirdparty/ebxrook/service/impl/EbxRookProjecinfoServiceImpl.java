package cn.ccoco.platform.thirdparty.ebxrook.service.impl;

import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.thirdparty.ebxrook.api.EbxRookProjectInfoService;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookProjecinfo;
import cn.ccoco.platform.thirdparty.ebxrook.mapper.EbxRookProjecinfoMapper;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookBoxesService;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookProjecinfoService;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 智慧式用电数据开放平台--项目信息 Service实现
 *
 * @author Ccoco
 * @date 2020-07-20 16:41:47
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EbxRookProjecinfoServiceImpl extends ServiceImpl<EbxRookProjecinfoMapper, EbxRookProjecinfo> implements IEbxRookProjecinfoService {

    @Autowired
    EbxRookProjectInfoService ebxRookProjectInfoService;
    @Autowired
    IEbxRookBoxesService ebxRookBoxesService;

    @Override
    public IPage<EbxRookProjecinfo> findEbxRookProjecinfos(QueryRequest request, EbxRookProjecinfo ebxRookProjecinfo) {
        LambdaQueryWrapper<EbxRookProjecinfo> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<EbxRookProjecinfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<EbxRookProjecinfo> findEbxRookProjecinfos(EbxRookProjecinfo ebxRookProjecinfo) {
	    LambdaQueryWrapper<EbxRookProjecinfo> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createEbxRookProjecinfo(EbxRookProjecinfo ebxRookProjecinfo) throws Exception {
        ebxRookProjecinfo = ebxRookProjectInfoService.getProjectInfo(ebxRookProjecinfo);
        this.save(ebxRookProjecinfo);
        ebxRookBoxesService.asyncBoxes(ebxRookProjecinfo.getProjectCode(),ebxRookProjecinfo.getDeptId(),true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEbxRookProjecinfo(EbxRookProjecinfo ebxRookProjecinfo) {
        this.saveOrUpdate(ebxRookProjecinfo);
    }

    @Override
    public void batchupdateEbxRookProjecinfo(String projectCodes,Long deptId) throws Exception{
        if(StringUtils.isEmpty(projectCodes)){
            throw new Exception("项目编码是空的");
        }
        if(null == deptId){
            throw new Exception("部门是空的");
        }
        String[] projectcodess = projectCodes.split(",");
        this.lambdaUpdate().in(EbxRookProjecinfo::getProjectCode,projectcodess).set(EbxRookProjecinfo::getDeptId,deptId).update();
        ebxRookBoxesService.asyncBoxes(projectCodes,deptId,false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEbxRookProjecinfo(EbxRookProjecinfo ebxRookProjecinfo) {
        LambdaQueryWrapper<EbxRookProjecinfo> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        EbxRookProjecinfo ebxRookProjecinfo = this.getById(id);
        ebxRookBoxesService.deleteEbxRookBoxes(ebxRookProjecinfo.getProjectCode());
        return SqlHelper.retBool(this.getBaseMapper().deleteById(id));
    }
}
