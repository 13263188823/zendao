package org.project.modules.zentao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.project.common.system.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 禅道补录对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="禅道补录对象", description="禅道补录对象")
@TableName("zt_workhistory")
public class ZentaoHisEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 标题 */
	@Excel(name="名称",width=255)
	@ApiModelProperty(value = "名称")

	private String name;
	/** SQL参数 */
	@Excel(name="备注",width=255)
	@ApiModelProperty(value = "备注")
	private String memo;

	/** status（状态--0:未入库未审核 1:未入库已审核 2:已入库未审核 3:已入库已审核） */
	@Excel(name = "补录状态", width = 255, dicCode = "status")
	@ApiModelProperty(value = "补录状态")
	private String status;

	@ApiModelProperty(value = "创建时间")
	@Excel(name = "创建时间", width = 255, dicCode = "createDate")
	@TableField(value = "createDate")
	private Date createDate;

	@ApiModelProperty(value = "更新时间")
	@Excel(name = "更新时间", width = 255, dicCode = "updateDate")
	@TableField(value = "updateDate")
	private Date updateDate;

	@ApiModelProperty(value = "修改人")
	@Excel(name = "修改人", width = 255, dicCode = "modifiedMan")
	@TableField(value = "modifiedMan")
	private String modifiedMan;

	@ApiModelProperty(value = "修改时间")
	@Excel(name = "修改时间", width = 255, dicCode = "modifiedDate")
	@TableField(value = "modifiedDate")
	private Date modifiedDate;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getModifiedMan() {
		return modifiedMan;
	}

	public void setModifiedMan(String modifiedMan) {
		this.modifiedMan = modifiedMan;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "ZentaoHisEntity{" +
				"name='" + name + '\'' +
				", memo='" + memo + '\'' +
				", status='" + status + '\'' +
				", createDate=" + createDate +
				", updateDate=" + updateDate +
				", modifiedMan='" + modifiedMan + '\'' +
				", modifiedDate=" + modifiedDate +
				"} " + super.toString();
	}
}
