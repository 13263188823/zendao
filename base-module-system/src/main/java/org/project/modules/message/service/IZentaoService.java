package org.project.modules.message.service;

import org.project.common.system.base.service.BaseService;
import org.project.common.system.base.service.JeecgService;
import org.project.modules.message.entity.SysMessage;
import org.project.modules.message.entity.ZtWorkHistoryEntity;
import org.project.modules.zentao.entity.ZtWorkhistory;

import java.util.List;

/**
 * 禅道处理服务
 */

public interface IZentaoService extends BaseService<ZtWorkHistoryEntity> {
      public List<ZtWorkHistoryEntity> findEntityAll();
      public List<ZtWorkHistoryEntity> findEntityAllCount();
      public void insertZHkEntity(ZtWorkHistoryEntity whe);
      public void updateEntity(String ids);
      public void updateEntityByNameAndDate(String name,String date);
      public List<ZtWorkhistory> findEntityAllByIds(String ids);
}
