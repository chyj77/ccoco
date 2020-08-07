package cn.ccoco.platform.thirdparty.ebxrook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ebx_rook_boxes")
@ApiModel("智慧式用电数据开放平台--项目内的所有设备")
public class EbxRookBoxes{

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 0 正常 1 删除
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * create_time
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * update_time
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;

    /**
     * update_time
     */
    @TableField("alias_name")
    private String aliasName;
    /**
     * unit
     */
    @TableField("unit")
    private String unit;
    /**
     * protocol
     */
    @TableField("protocol")
    private String protocol;
    /**
     * build
     */
    @TableField("build")
    private String build;
    /**
     * name
     */
    @TableField("name")
    private String name;
    /**
     * mac
     */
    @TableField("mac")
    private String mac;
    /**
     * room
     */
    @TableField("room")
    private String room;
    /**
     * linkman
     */
    @TableField("linkman")
    private String linkman;
    /**
     * phone
     */
    @TableField("phone")
    private String phone;
    /**
     * online
     */
    @TableField("online")
    private Integer online;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    public EbxRookBoxes(JSONObject jsonObject, String projectCode, String mac,Long deptId){
        this.setAliasName(jsonObject.optString("aliasName"));
        this.setBuild(jsonObject.optString("build"));
        this.setLinkman(jsonObject.optString("linkman"));
        this.setMac(mac);
        this.setName(jsonObject.optString("name"));
        this.setOnline(jsonObject.optInt("online"));
        this.setPhone(jsonObject.optString("phone"));
        this.setProjectCode(projectCode);
        this.setProtocol(jsonObject.optString("protocol"));
        this.setRoom(jsonObject.optString("room"));
        this.setUnit(jsonObject.optString("unit"));
        this.setDeptId(deptId);
    }

    public EbxRookBoxes(EbxRookBoxes rookBoxes, JSONObject jsonObject, String projectCode, String mac,Long deptId){
        if(null!=rookBoxes){
            this.id = rookBoxes.getId();
            this.deleted = rookBoxes.getDeleted();
        }
        this.setAliasName(jsonObject.optString("aliasName"));
        this.setBuild(jsonObject.optString("build"));
        this.setLinkman(jsonObject.optString("linkman"));
        this.setMac(mac);
        this.setName(jsonObject.optString("name"));
        this.setOnline(jsonObject.optInt("online"));
        this.setPhone(jsonObject.optString("phone"));
        this.setProjectCode(projectCode);
        this.setProtocol(jsonObject.optString("protocol"));
        this.setRoom(jsonObject.optString("room"));
        this.setUnit(jsonObject.optString("unit"));
        this.setDeptId(deptId);
    }

}
