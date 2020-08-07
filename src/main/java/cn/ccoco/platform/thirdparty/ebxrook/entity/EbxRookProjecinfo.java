package cn.ccoco.platform.thirdparty.ebxrook.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;

/**
 * 智慧式用电数据开放平台--项目信息 Entity
 *
 * @author Ccoco
 * @date 2020-07-20 16:41:47
 */
@Data
@TableName("ebx_rook_project_info")
public class EbxRookProjecinfo {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 0  正常 1 删除
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 项目编号
     */
    @NotBlank(message = "{required}")
    @TableField("project_code")
    private String projectCode;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 位置：省
     */
    @TableField("province_name")
    private String provinceName;

    /**
     * 位置：市
     */
    @TableField("city_name")
    private String cityName;

    /**
     * 位置：区
     */
    @TableField("county_name")
    private String countyName;

    /**
     * 位置：街道
     */
    @TableField("street")
    private String street;

    /**
     * 项目图片URL
     */
    @TableField("img_url")
    private String imgUrl;

    /**
     * 项目位置经度
     */
    @TableField("longitude")
    private Double longitude;

    /**
     * 项目位置纬度
     */
    @TableField("latitude")
    private Double latitude;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

}
