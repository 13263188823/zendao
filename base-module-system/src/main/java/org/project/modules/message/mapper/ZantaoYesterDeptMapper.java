package org.project.modules.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.project.modules.message.entity.DeptEntity;
import org.project.modules.message.entity.UserEntity;

import java.util.List;

/**
 禅道处理Mapper
 */
public interface ZantaoYesterDeptMapper extends BaseMapper<DeptEntity> {
      public List<DeptEntity> findDeptAll();
}
