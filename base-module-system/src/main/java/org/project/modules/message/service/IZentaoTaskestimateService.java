package org.project.modules.message.service;

import org.project.common.system.base.service.BaseService;
import org.project.modules.message.entity.TaskEntity;
import org.project.modules.message.entity.UserEntity;
import org.project.modules.message.entity.ZtTaskestimateEntity;

import java.util.List;

/**
 * 禅道处理服务
 */

public interface IZentaoTaskestimateService extends BaseService<ZtTaskestimateEntity> {
      public List<ZtTaskestimateEntity> findTaskestimateByUser(String name,String beginTime,String endTime);
}
