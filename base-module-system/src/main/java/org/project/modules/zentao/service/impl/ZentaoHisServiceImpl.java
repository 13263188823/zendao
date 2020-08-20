package org.project.modules.zentao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.project.common.constant.CacheConstant;
import org.project.common.system.query.QueryGenerator;
import org.project.modules.zentao.entity.ZentaoHisEntity;
import org.project.modules.zentao.mapper.ZentaoHisMapper;
import org.project.modules.zentao.service.IZentaoHisService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 */
@Service
@DS("multi-datasource1")
public class ZentaoHisServiceImpl extends ServiceImpl<ZentaoHisMapper, ZentaoHisEntity> implements IZentaoHisService {

	@Resource
	ZentaoHisMapper zentaoHisMapper;

	/**
	 * 事务控制在service层面
	 * 加上注解：@Transactional，声明的方法就是一个独立的事务（有异常DB操作全部回滚）
	 */
	@Override
	@Transactional
	public void testTran() {
		Date date = new Date(System.currentTimeMillis());
		ZentaoHisEntity de = new ZentaoHisEntity();
		de.setName("王子辰");
		de.setStatus("4");
		de.setMemo("补录");
		de.setCreateDate(date);
		de.setModifiedDate(date);
		de.setModifiedMan("王子辰");
		zentaoHisMapper.insert(de);
		return ;
	}


	/**
	 * 缓存注解测试： redis
	 */
	@Override
	@Cacheable(cacheNames = CacheConstant.TEST_DEMO_CACHE, key = "#id")
	public ZentaoHisEntity getByIdCacheable(String id) {
		ZentaoHisEntity t = zentaoHisMapper.selectById(id);
		System.err.println("---未读缓存，读取数据库---");
		System.err.println(t);
		return t;
	}

	@Override
	public IPage<ZentaoHisEntity> queryListWithPermission(int pageSize,int pageNo) {
		Page<ZentaoHisEntity> page = new Page<>(pageNo, pageSize);
		//编程方式，获取当前请求的数据权限规则SQL片段
		String sql = QueryGenerator.installAuthJdbc(ZentaoHisEntity.class);
		return this.baseMapper.queryListWithPermission(page, sql);
	}

	public static void main(String[] args) {
		Date date = new Date(System.currentTimeMillis());
		ZentaoHisEntity de = new ZentaoHisEntity();
		de.setName("王子辰");
		de.setStatus("4");
		de.setMemo("补录");
		de.setCreateDate(date);
		de.setUpdateDate(date);
		de.setModifiedDate(date);
		de.setModifiedMan("王子辰");
		String str  = JSONObject.toJSONString(de);
		System.out.println(str);
	}
}
