package org.project.modules.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.project.modules.message.entity.TaskEntity;
import org.project.modules.message.entity.UserEntity;
import org.project.modules.message.entity.ZtTaskestimateEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 禅道处理Mapper
 */
public interface ZantaoTaskestimateMapper extends BaseMapper<ZtTaskestimateEntity> {
      public List<ZtTaskestimateEntity> findTaskestimateByUser(ZtTaskestimateEntity ztTaskestimateEntity);
}
