package org.project.modules.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;
import org.project.modules.message.entity.DeptEntity;
import org.project.modules.message.entity.UserEntity;
import org.project.modules.message.entity.ZtWorkHistoryEntity;
import org.project.modules.zentao.entity.ZtWorkhistory;

import java.util.List;

/**
 禅道处理Mapper
 */
public interface ZantaoYesterMapper extends BaseMapper<UserEntity> {
      public List<UserEntity> findUserAll();
}
