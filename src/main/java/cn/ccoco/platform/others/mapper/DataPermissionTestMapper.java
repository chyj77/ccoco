package cn.ccoco.platform.others.mapper;

import cn.ccoco.platform.common.annotation.DataPermission;
import cn.ccoco.platform.others.entity.DataPermissionTest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author MrBird
 */
@DataPermission(methods = {"selectPage"})
public interface DataPermissionTestMapper extends BaseMapper<DataPermissionTest> {

}
