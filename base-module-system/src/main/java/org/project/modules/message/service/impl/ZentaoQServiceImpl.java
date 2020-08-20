package org.project.modules.message.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.project.common.system.base.service.impl.BaseServiceImpl;
import org.project.modules.message.entity.ZtUserQuickQueryEntity;
import org.project.modules.message.mapper.ZantaoQMapper;
import org.project.modules.message.service.IZentaoQService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
@DS("multi-datasource1")
public class ZentaoQServiceImpl extends BaseServiceImpl<ZantaoQMapper, ZtUserQuickQueryEntity> implements IZentaoQService {

    @Resource
    private ZantaoQMapper ztQMapper;

    @Override
    public List<ZtUserQuickQueryEntity> findEntityAll() {
        return ztQMapper.findEntityAll();
    }

    @Override
    public void insertEntity(ZtUserQuickQueryEntity ztUserQuickQueryEntity) {
        ztQMapper.insertZtUserQuickQueryEntity(ztUserQuickQueryEntity);
    }
}
