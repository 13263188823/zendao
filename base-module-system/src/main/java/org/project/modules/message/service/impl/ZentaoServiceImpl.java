package org.project.modules.message.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.project.common.system.base.service.BaseService;
import org.project.common.system.base.service.impl.BaseServiceImpl;
import org.project.common.system.base.service.impl.JeecgServiceImpl;
import org.project.modules.message.entity.SysMessage;
import org.project.modules.message.entity.ZtUserQuickQueryEntity;
import org.project.modules.message.entity.ZtWorkHistoryEntity;
import org.project.modules.message.mapper.SysMessageMapper;
import org.project.modules.message.mapper.ZantaoMapper;
import org.project.modules.message.service.ISysMessageService;
import org.project.modules.message.service.IZentaoService;
import org.project.modules.zentao.entity.ZtWorkhistory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@Service
@DS("multi-datasource1")
public class ZentaoServiceImpl extends BaseServiceImpl<ZantaoMapper, ZtWorkHistoryEntity> implements IZentaoService {

    @Resource
    private ZantaoMapper ztMapper;

    @Override
    public List<ZtWorkHistoryEntity> findEntityAll() {
        return ztMapper.findEntityAll();
    }

    @Override
    public List<ZtWorkHistoryEntity> findEntityAllCount() {
        return ztMapper.findEntityAllCount();
    }

    @Override
    public void insertZHkEntity(ZtWorkHistoryEntity whe) {
        ztMapper.insertZHkEntity(whe);
    }

    @Override
    public void updateEntity(String id) {
        ztMapper.updateEntity(id);
    }

    @Override
    public void updateEntityByNameAndDate(String name,String date) {

        ztMapper.updateEntityByNameAndDate(name, date);
    }

    @Override
    public List<ZtWorkhistory> findEntityAllByIds(String ids) {
        return ztMapper.findEntityAllByIds(ids);
    }


}
