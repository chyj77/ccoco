package cn.ccoco.platform.thirdparty.ebxrook.service;

import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookProjecinfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 智慧式用电数据开放平台--项目信息 Service接口
 *
 * @author Ccoco
 * @date 2020-07-20 16:41:47
 */
public interface IEbxRookProjecinfoService extends IService<EbxRookProjecinfo> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param ebxRookProjecinfo ebxRookProjecinfo
     * @return IPage<EbxRookProjecinfo>
     */
    IPage<EbxRookProjecinfo> findEbxRookProjecinfos(QueryRequest request, EbxRookProjecinfo ebxRookProjecinfo);

    /**
     * 查询（所有）
     *
     * @param ebxRookProjecinfo ebxRookProjecinfo
     * @return List<EbxRookProjecinfo>
     */
    List<EbxRookProjecinfo> findEbxRookProjecinfos(EbxRookProjecinfo ebxRookProjecinfo);


    /**
     * 新增
     *
     * @param ebxRookProjecinfo ebxRookProjecinfo
     */
    void createEbxRookProjecinfo(EbxRookProjecinfo ebxRookProjecinfo) throws Exception;

    /**
     * 修改
     *
     * @param ebxRookProjecinfo ebxRookProjecinfo
     */
    void updateEbxRookProjecinfo(EbxRookProjecinfo ebxRookProjecinfo);

    void batchupdateEbxRookProjecinfo(String projectCodes,Long deptId)throws Exception;

    /**
     * 删除
     *
     * @param ebxRookProjecinfo ebxRookProjecinfo
     */
    void deleteEbxRookProjecinfo(EbxRookProjecinfo ebxRookProjecinfo);

    boolean removeById(Serializable id);
}
