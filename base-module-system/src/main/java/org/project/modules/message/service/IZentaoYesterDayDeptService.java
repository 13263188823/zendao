package org.project.modules.message.service;

import org.project.common.system.base.service.BaseService;
import org.project.modules.message.entity.DeptEntity;
import org.project.modules.message.entity.UserEntity;

import java.util.List;

/**
 * 禅道处理服务
 */

public interface IZentaoYesterDayDeptService extends BaseService<DeptEntity> {
      public List<DeptEntity> findDeptAll();
}
