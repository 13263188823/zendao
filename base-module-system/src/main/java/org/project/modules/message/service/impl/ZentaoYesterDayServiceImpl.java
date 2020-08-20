package org.project.modules.message.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.project.common.system.base.service.impl.BaseServiceImpl;
import org.project.modules.message.entity.UserEntity;
import org.project.modules.message.entity.ZtWorkHistoryEntity;
import org.project.modules.message.mapper.ZantaoMapper;
import org.project.modules.message.mapper.ZantaoYesterMapper;
import org.project.modules.message.service.IZentaoService;
import org.project.modules.message.service.IZentaoYesterDayService;
import org.project.modules.zentao.entity.ZtWorkhistory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
@DS("multi-datasource2")
public class ZentaoYesterDayServiceImpl extends BaseServiceImpl<ZantaoYesterMapper, UserEntity> implements IZentaoYesterDayService {

    @Resource
    private ZantaoYesterMapper ztYesMapper;

    @Override
    public List<UserEntity> findUserAll() {
        return ztYesMapper.findUserAll();
    }
}
