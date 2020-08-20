package org.project.modules.message.service;

import org.project.common.system.base.service.BaseService;
import org.project.modules.message.entity.ZtUserQuickQueryEntity;
import org.project.modules.message.entity.ZtWorkHistoryEntity;

import java.util.List;

/**
 * 禅道快查服务
 */

public interface IZentaoQService extends BaseService<ZtUserQuickQueryEntity> {
      public List<ZtUserQuickQueryEntity> findEntityAll();
      public void insertEntity(ZtUserQuickQueryEntity ztUserQuickQueryEntity);
}
