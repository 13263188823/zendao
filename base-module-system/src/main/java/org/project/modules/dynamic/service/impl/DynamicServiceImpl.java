package org.project.modules.dynamic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.project.common.constant.CacheConstant;
import org.project.common.system.query.QueryGenerator;
import org.project.modules.dynamic.entity.DynamicEntity;
import org.project.modules.dynamic.mapper.DynamicMapper;
import org.project.modules.dynamic.service.IDynamicService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, DynamicEntity> implements IDynamicService {

	@Resource
	DynamicMapper dynamicMapper;

	/**
	 * 事务控制在service层面
	 * 加上注解：@Transactional，声明的方法就是一个独立的事务（有异常DB操作全部回滚）
	 */
	@Override
	@Transactional
	public void testTran() {
		DynamicEntity de = new DynamicEntity();
		de.setTitle("1");
		de.setStatus("2");
		de.setSqlvalue("3");
		dynamicMapper.insert(de);
		return ;
	}


	/**
	 * 缓存注解测试： redis
	 */
	@Override
	@Cacheable(cacheNames = CacheConstant.TEST_DEMO_CACHE, key = "#id")
	public DynamicEntity getByIdCacheable(String id) {
		DynamicEntity t = dynamicMapper.selectById(id);
		System.err.println("---未读缓存，读取数据库---");
		System.err.println(t);
		return t;
	}


	@Override
	public IPage<DynamicEntity> queryListWithPermission(int pageSize,int pageNo) {
		Page<DynamicEntity> page = new Page<>(pageNo, pageSize);
		//编程方式，获取当前请求的数据权限规则SQL片段
		String sql = QueryGenerator.installAuthJdbc(DynamicEntity.class);
		return this.baseMapper.queryListWithPermission(page, sql);
	}

}
