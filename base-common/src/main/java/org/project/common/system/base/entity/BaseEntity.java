package org.project.common.system.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *   通用字段基础类
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ID */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "ID")
	private String id;
	/** 创建人 */
	@ApiModelProperty(value = "创建人")
	@Excel(name = "创建人", width = 255)
	private String createMan;
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间")
	@Excel(name = "创建时间", width = 255, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/** 更新人 */
	@ApiModelProperty(value = "更新人")
	@Excel(name = "更新人", width = 255)
	private String modifiyMan;
	/** 更新时间 */
	@ApiModelProperty(value = "更新时间")
	@Excel(name = "更新时间", width = 255, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifiyMan() {
		return modifiyMan;
	}

	public void setModifiyMan(String modifiyMan) {
		this.modifiyMan = modifiyMan;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "BaseEntity{" +
				"id='" + id + '\'' +
				", createMan='" + createMan + '\'' +
				", createTime=" + createTime +
				", modifiyMan='" + modifiyMan + '\'' +
				", updateTime=" + updateTime +
				'}';
	}
}
