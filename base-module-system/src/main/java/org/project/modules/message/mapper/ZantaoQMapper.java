package org.project.modules.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.project.modules.message.entity.ZtUserQuickQueryEntity;
import org.project.modules.message.entity.ZtWorkHistoryEntity;

import java.util.List;

/**
 禅道处理Mapper
 */
public interface ZantaoQMapper extends BaseMapper<ZtUserQuickQueryEntity> {
      public List<ZtUserQuickQueryEntity> findEntityAll();
      public void insertZtUserQuickQueryEntity(ZtUserQuickQueryEntity zqqe);
}
