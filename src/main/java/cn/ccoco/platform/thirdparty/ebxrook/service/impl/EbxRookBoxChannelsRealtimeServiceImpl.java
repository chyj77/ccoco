package cn.ccoco.platform.thirdparty.ebxrook.service.impl;

import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxChannelsRealtime;
import cn.ccoco.platform.thirdparty.ebxrook.mapper.EbxRookBoxChannelsRealtimeMapper;
import cn.ccoco.platform.thirdparty.ebxrook.service.IEbxRookBoxChannelsRealtimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 智慧式用电数据开放平台--设备实时状态数据 Service实现
 *
 * @author Ccoco
 * @date 2020-07-23 14:15:54
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EbxRookBoxChannelsRealtimeServiceImpl extends ServiceImpl<EbxRookBoxChannelsRealtimeMapper, EbxRookBoxChannelsRealtime> implements IEbxRookBoxChannelsRealtimeService {

    private final EbxRookBoxChannelsRealtimeMapper ebxRookBoxChannelsRealtimeMapper;

    @Override
    public IPage<EbxRookBoxChannelsRealtime> findEbxRookBoxChannelsRealtimes(QueryRequest request, EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        LambdaQueryWrapper<EbxRookBoxChannelsRealtime> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<EbxRookBoxChannelsRealtime> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<EbxRookBoxChannelsRealtime> findEbxRookBoxChannelsRealtimes(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
	    LambdaQueryWrapper<EbxRookBoxChannelsRealtime> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        this.save(ebxRookBoxChannelsRealtime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        this.saveOrUpdate(ebxRookBoxChannelsRealtime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime) {
        LambdaQueryWrapper<EbxRookBoxChannelsRealtime> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
