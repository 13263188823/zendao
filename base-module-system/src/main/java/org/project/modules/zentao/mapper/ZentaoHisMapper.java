package org.project.modules.zentao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.project.modules.dynamic.entity.DynamicEntity;
import org.project.modules.zentao.entity.ZentaoHisEntity;

import java.util.List;

/**
 * mapper接口
 */
public interface ZentaoHisMapper extends BaseMapper<ZentaoHisEntity> {

	public List<ZentaoHisEntity> getDemoByName(@Param("name") String name);
	
	/**
	 * 查询列表数据 直接传数据权限的sql进行数据过滤
	 * @param page
	 * @param permissionSql
	 * @return
	 */
	public IPage<ZentaoHisEntity> queryListWithPermission(Page<ZentaoHisEntity> page, @Param("permissionSql") String permissionSql);

}
