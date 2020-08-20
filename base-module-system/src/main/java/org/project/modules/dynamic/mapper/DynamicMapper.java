package org.project.modules.dynamic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.project.modules.demo.test.entity.JeecgDemo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.project.modules.dynamic.entity.DynamicEntity;

/**
 * mapper接口
 */
public interface DynamicMapper extends BaseMapper<DynamicEntity> {

	public List<DynamicEntity> getDemoByName(@Param("name") String name);
	
	/**
	 * 查询列表数据 直接传数据权限的sql进行数据过滤
	 * @param page
	 * @param permissionSql
	 * @return
	 */
	public IPage<DynamicEntity> queryListWithPermission(Page<DynamicEntity> page, @Param("permissionSql") String permissionSql);

}
