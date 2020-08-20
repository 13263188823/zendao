package org.project.modules.zentao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.project.common.system.base.service.BaseService;
import org.project.modules.dynamic.entity.DynamicEntity;
import org.project.modules.zentao.entity.ZentaoHisEntity;

/**
 *
 */

public interface IZentaoHisService extends BaseService<ZentaoHisEntity> {
	
	public void testTran();
	
	public ZentaoHisEntity getByIdCacheable(String id);
	
	/**
	 * 查询列表数据 在service中获取数据权限sql信息
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	IPage<ZentaoHisEntity> queryListWithPermission(int pageSize, int pageNo);
}
