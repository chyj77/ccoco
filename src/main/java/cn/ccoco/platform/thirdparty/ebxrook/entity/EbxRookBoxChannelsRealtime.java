package cn.ccoco.platform.thirdparty.ebxrook.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * 智慧式用电数据开放平台--设备实时状态数据 Entity
 *
 * @author Ccoco
 * @date 2020-07-23 14:15:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ebx_rook_box_channels_realtime")
public class EbxRookBoxChannelsRealtime {

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
    private String createTime;

    /**
     *
     */
    @TableField("update_time")
    private String updateTime;

    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;

    /**
     * 设备号
     */
    @TableField("mac")
    private String mac;

    /**
     * 线路地址
     */
    @TableField("addr")
    private Integer addr;

    /**
     * 线路名称
     */
    @TableField("title")
    private String title;

    /**
     * 线路有效性(和通信模块的连通性):true有效;false无效
     */
    @TableField("validity")
    private Boolean validity;

    /**
     * 线路开关状态:true开;fase关
     */
    @TableField("oc")
    private Boolean oc;

    /**
     * 线路开关状态变更时间
     */
    @TableField("oc_time")
    private String ocTime;

    /**
     * 远程控制合闸分闸true允许;false不允许一般开关被手动分闸后，此值为false
     */
    @TableField("enable_net_ctrl")
    private Boolean enableNetCtrl;

    /**
     * 设备通信模块是否在线（-1未曾连接；0离线；1在线）
     */
    @TableField("online")
    private Integer online;

    /**
     * 线路当前电量（又称为刻度电量）
     */
    @TableField("power")
    private Double power;

    /**
     * 过功门限值
     */
    @TableField("mxgg")
    private String mxgg;

    /**
     * 过流门限值
     */
    @TableField("mxgl")
    private String mxgl;

    /**
     * 类型：220单相;380三相
     */
    @TableField("line_type")
    private String lineType;

    /**
     * 告警状态，0或128表示告警取消;非0表示存在告警
     */
    @TableField("alarm")
    private Integer alarm;

    /**
     * 线路规格
     */
    @TableField("specification")
    private String specification;

    /**
     * -1为进线直连，非-1表示电量目标节点号(接入的汇电线路)
     */
    @TableField("gather_addr")
    private Integer gatherAddr;

    /**
     * 控制标记(true/false)说明：设备只转存该标记，具体逻辑由客户端UI实现
     */
    @TableField("control")
    private Boolean control;

    /**
     * 显示标记(true/false)注意：设备只转存该标记，具体逻辑由客户端UI实现
     */
    @TableField("visibility")
    private Boolean visibility;

    /**
     * 总线标记(1:总线/0:非总线)
     */
    @TableField("mainLine")
    private String mainLine;

    /**
     * 单相情况:	漏电流
     */
    @TableField("aLd")
    private Double aLd;

    /**
     * 三相情况：	漏电流
     */
    @TableField("gLd")
    private Double gLd;

    /**
     * 三相情况：	平均电流
     */
    @TableField("gA")
    private Double gA;

    /**
     * 三相情况：	壳温度
     */
    @TableField("gT")
    private Double gT;

    /**
     * 三相情况：	平均电压
     */
    @TableField("gV")
    private Double gV;

    /**
     * 三相情况：	功率和值
     */
    @TableField("gW")
    private Double gW;

    /**
     * 三相情况：	合相功率因素
     */
    @TableField("gPF")
    private Double gPF;

    /**
     * 三相情况：	A相电流
     */
    @TableField("aA")
    private Double aA;

    /**
     * 三相情况：	A相温度
     */
    @TableField("aT")
    private Double aT;

    /**
     * 三相情况：	A相电压
     */
    @TableField("aV")
    private Double aV;

    /**
     * 三相情况：	A相功率
     */
    @TableField("aW")
    private Double aW;

    /**
     * 三相情况：	A 相功率因素
     */
    @TableField("aPF")
    private Double aPF;

    /**
     * 三相情况：	B相电流
     */
    @TableField("bA")
    private Double bA;

    /**
     * 三相情况：	B相温度
     */
    @TableField("bT")
    private Double bT;

    /**
     * 三相情况：	B相电压
     */
    @TableField("bV")
    private Double bV;

    /**
     * 三相情况：	B相功率
     */
    @TableField("bW")
    private Double bW;

    /**
     * 三相情况：	B 相功率因素
     */
    @TableField("bPF")
    private Double bPF;

    /**
     * 三相情况：	C相电流
     */
    @TableField("cA")
    private Double cA;

    /**
     * 三相情况：	C相温度
     */
    @TableField("cT")
    private Double cT;

    /**
     * 三相情况：	C相电压
     */
    @TableField("cV")
    private Double cV;

    /**
     * 三相情况：	C相功率
     */
    @TableField("cW")
    private Double cW;

    /**
     * 三相情况：	C 相功率因素
     */
    @TableField("cPF")
    private Double cPF;

    /**
     * 三相情况：	零线电流
     */
    @TableField("nA")
    private Double nA;

    /**
     * 三相情况：	零线温度
     */
    @TableField("nT")
    private Double nT;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    public EbxRookBoxChannelsRealtime(JSONObject jsonObject, String projectCode) {

// 项目编号
        this.setProjectCode(projectCode);
// 设备号
        this.setMac(jsonObject.optString("mac"));
// 线路地址
        this.setAddr(jsonObject.optInt("addr"));
// 线路名称
        this.setTitle(jsonObject.optString("title"));
// 线路有效性(和通信模块的连通性)
        this.setValidity(jsonObject.optBoolean("validity"));
// 线路开关状态:true开;fase关
        this.setOc(jsonObject.optBoolean("oc"));
// 线路开关状态变更时间
        this.setOcTime(jsonObject.optString("ocTime"));
// 远程控制合闸分闸true允许;false不允许一般开关被手动分闸后，此值为false
        this.setEnableNetCtrl(jsonObject.optBoolean("enableNetCtrl"));
// 更新时间
        this.setUpdateTime(jsonObject.optString("updateTime"));
// 设备通信模块是否在线（-1未曾连接；0离线；1在线）
        this.setOnline(jsonObject.optInt("online"));
// 线路当前电量（又称为刻度电量）
        this.setPower(jsonObject.optDouble("power"));
// 过功门限值
        this.setMxgg(jsonObject.optString("mxgg"));
// 过流门限值
        this.setMxgl(jsonObject.optString("mxgl"));
// 类型：220单相;380三相
        this.setLineType(jsonObject.optString("lineType"));
// 告警状态，0或128表示告警取消;非0表示存在告警
        this.setAlarm(jsonObject.optInt("alarm"));
// 线路规格
        this.setSpecification(jsonObject.optString("specification"));
// -1为进线直连，非-1表示电量目标节点号(接入的汇电线路)
        this.setGatherAddr(jsonObject.optInt("gatherAddr"));
// 控制标记(true/false)
        this.setControl(jsonObject.optBoolean("control"));
// 显示标记(true/false)
        this.setVisibility(jsonObject.optBoolean("visibility"));
// 总线标记(1:总线/0:非总线)
        this.setMainLine(jsonObject.optString("mainLine"));
// 单相情况:漏电流
        this.setALd(jsonObject.optDouble("aLd"));
// 三相情况：漏电流
        this.setGLd(jsonObject.optDouble("gLd"));
// 三相情况：平均电流
        this.setGA(jsonObject.optDouble("gA"));
// 三相情况：壳温度
        this.setGT(jsonObject.optDouble("gT"));
// 三相情况：平均电压
        this.setGV(jsonObject.optDouble("gV"));
// 三相情况：功率和值
        this.setGW(jsonObject.optDouble("gW"));
// 三相情况：合相功率因素
        this.setGPF(jsonObject.optDouble("gPF"));
// 三相情况：a相电流
        this.setAA(jsonObject.optDouble("aA"));
// 三相情况：a相温度
        this.setAT(jsonObject.optDouble("aT"));
// 三相情况：a相电压
        this.setAV(jsonObject.optDouble("aV"));
// 三相情况：a相功率
        this.setAW(jsonObject.optDouble("aW"));
// 三相情况：a 相功率因素
        this.setAPF(jsonObject.optDouble("aPF"));
// 三相情况：b相电流
        this.setBA(jsonObject.optDouble("bA"));
// 三相情况：b相温度
        this.setBT(jsonObject.optDouble("bT"));
// 三相情况：b相电压
        this.setBV(jsonObject.optDouble("bV"));
// 三相情况：b相功率
        this.setBW(jsonObject.optDouble("bW"));
// 三相情况：b 相功率因素
        this.setBPF(jsonObject.optDouble("bPF"));
// 三相情况：c相电流
        this.setCA(jsonObject.optDouble("cA"));
// 三相情况：c相温度
        this.setCT(jsonObject.optDouble("cT"));
// 三相情况：c相电压
        this.setCV(jsonObject.optDouble("cV"));
// 三相情况：c相功率
        this.setCW(jsonObject.optDouble("cW"));
// 三相情况：c 相功率因素
        this.setCPF(jsonObject.optDouble("cPF"));
// 三相情况：零线电流
        this.setNA(jsonObject.optDouble("nA"));
// 三相情况：零线温度
        this.setNT(jsonObject.optDouble("nT"));

    }
}
