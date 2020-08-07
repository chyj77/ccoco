package cn.ccoco.platform.thirdparty.ebxrook.service;

import cn.ccoco.platform.common.entity.QueryRequest;
import cn.ccoco.platform.system.entity.User;
import cn.ccoco.platform.thirdparty.ebxrook.entity.EbxRookBoxes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 智慧式用电数据开放平台--项目信息 Service接口
 *
 * @author Ccoco
 * @date 2020-07-20 16:41:47
 */
public interface IEbxRookBoxesService extends IService<EbxRookBoxes> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param ebxRookBoxes ebxRookBoxes
     * @return IPage<EbxRookBoxes>
     */
    IPage<EbxRookBoxes> findEbxRookBoxess(QueryRequest request, EbxRookBoxes ebxRookBoxes, User user);

    /**
     * 查询（所有）
     *
     * @param ebxRookBoxes ebxRookBoxes
     * @return List<EbxRookBoxes>
     */
    List<EbxRookBoxes> findEbxRookBoxess(EbxRookBoxes ebxRookBoxes);


    /**
     * 新增
     *
     * @param ebxRookBoxes ebxRookBoxes
     */
    void createEbxRookBoxes(EbxRookBoxes ebxRookBoxes) throws Exception;

    /**
     * 修改
     *
     * @param ebxRookBoxes ebxRookBoxes
     */
    void updateEbxRookBoxes(EbxRookBoxes ebxRookBoxes);

    /**
     * 删除
     *
     * @param ebxRookBoxes ebxRookBoxes
     */
    void deleteEbxRookBoxes(EbxRookBoxes ebxRookBoxes);

    void deleteEbxRookBoxes(String projectCode);

    void asyncBoxes(String projectCodes,Long deptId,boolean async)throws Exception;

    long findTotalBox(String deptIds);
    long findOnlineBox(String deptIds);
    long findUnderlineBox(String deptIds);
}
