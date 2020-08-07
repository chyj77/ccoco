package cn.ccoco.platform.thirdparty.ebxrook.service;

import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxChannelsRealtime;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 智慧式用电数据开放平台--设备实时状态数据 Service接口
 *
 * @author Ccoco
 * @date 2020-07-23 14:15:54
 */
public interface IEbxRookBoxChannelsRealtimeService extends IService<EbxRookBoxChannelsRealtime> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param ebxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime
     * @return IPage<EbxRookBoxChannelsRealtime>
     */
    IPage<EbxRookBoxChannelsRealtime> findEbxRookBoxChannelsRealtimes(QueryRequest request, EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime);

    /**
     * 查询（所有）
     *
     * @param ebxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime
     * @return List<EbxRookBoxChannelsRealtime>
     */
    List<EbxRookBoxChannelsRealtime> findEbxRookBoxChannelsRealtimes(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime);

    /**
     * 新增
     *
     * @param ebxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime
     */
    void createEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime);

    /**
     * 修改
     *
     * @param ebxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime
     */
    void updateEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime);

    /**
     * 删除
     *
     * @param ebxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime
     */
    void deleteEbxRookBoxChannelsRealtime(EbxRookBoxChannelsRealtime ebxRookBoxChannelsRealtime);
}
