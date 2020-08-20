package org.project.modules.dynamic.service;

import org.project.common.system.base.service.BaseService;
import org.project.common.system.base.service.JeecgService;
import org.project.modules.demo.test.entity.JeecgDemo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.project.modules.dynamic.entity.DynamicEntity;

/**
 *
 */

public interface IDynamicService extends BaseService<DynamicEntity> {
	
	public void testTran();
	
	public DynamicEntity getByIdCacheable(String id);
	
	/**
	 * 查询列表数据 在service中获取数据权限sql信息
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	IPage<DynamicEntity> queryListWithPermission(int pageSize, int pageNo);
}
