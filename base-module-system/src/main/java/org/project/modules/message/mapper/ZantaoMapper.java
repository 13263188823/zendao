package org.project.modules.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.project.modules.message.entity.SysMessage;
import org.project.modules.message.entity.ZtWorkHistoryEntity;
import org.project.modules.zentao.entity.ZtWorkhistory;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 禅道处理Mapper
 */
public interface ZantaoMapper extends BaseMapper<ZtWorkHistoryEntity> {
      public List<ZtWorkHistoryEntity> findEntityAll();
      public List<ZtWorkHistoryEntity> findEntityAllCount();
      public void insertZHkEntity(ZtWorkHistoryEntity whe);
      public void updateEntity(String ids);
      public void updateEntityByNameAndDate(String name,String date);
      public List<ZtWorkhistory> findEntityAllByIds(String ids);
}
