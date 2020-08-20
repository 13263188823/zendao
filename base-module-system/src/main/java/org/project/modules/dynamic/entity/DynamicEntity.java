package org.project.modules.dynamic.entity;

import java.io.Serializable;

import org.project.common.system.base.entity.BaseEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: jeecg 测试demo 
 * @Author: jeecg-boot 
 * @Date:	2018-12-29 
 * @Version:V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="动态SQL对象", description="动态SQL对象")
@TableName("dynamic_sql")
public class DynamicEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 标题 */
	@Excel(name="标题",width=255)
	@ApiModelProperty(value = "标题")
	private String title;
	/** SQL参数 */
	@Excel(name="SQL参数",width=255)
	@ApiModelProperty(value = "SQL参数")
	private String sqlvalue;

	/** SQL状态（0 未激活 ,1 已激活） */
	@ApiModelProperty(value = "SQL状态")
	@Excel(name = "SQL状态", width = 255, dicCode = "status")
	private String status;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSqlvalue() {
		return sqlvalue;
	}

	public void setSqlvalue(String sqlvalue) {
		this.sqlvalue = sqlvalue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DynamicEntity{" +
				"title='" + title + '\'' +
				", sqlvalue='" + sqlvalue + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
