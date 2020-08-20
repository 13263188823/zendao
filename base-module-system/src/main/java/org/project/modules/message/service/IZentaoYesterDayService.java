package org.project.modules.message.service;

import org.apache.poi.ss.formula.functions.T;
import org.project.common.system.base.service.BaseService;
import org.project.modules.message.entity.DeptEntity;
import org.project.modules.message.entity.UserEntity;
import org.project.modules.message.entity.ZtWorkHistoryEntity;
import org.project.modules.zentao.entity.ZtWorkhistory;

import java.util.List;

/**
 * 禅道处理服务
 */

public interface IZentaoYesterDayService extends BaseService<UserEntity> {
      public List<UserEntity> findUserAll();
}
