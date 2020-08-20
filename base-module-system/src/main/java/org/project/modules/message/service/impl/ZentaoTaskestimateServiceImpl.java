package org.project.modules.message.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.project.common.system.base.service.impl.BaseServiceImpl;
import org.project.modules.message.entity.ZtTaskestimateEntity;
import org.project.modules.message.mapper.ZantaoTaskestimateMapper;
import org.project.modules.message.service.IZentaoTaskestimateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
@DS("multi-datasource2")
public class ZentaoTaskestimateServiceImpl extends BaseServiceImpl<ZantaoTaskestimateMapper, ZtTaskestimateEntity> implements IZentaoTaskestimateService {

    @Resource
    private ZantaoTaskestimateMapper ztYesTaskEntityMapper;

    @Override
    public List<ZtTaskestimateEntity> findTaskestimateByUser(String name, String beginTime, String endTime) {

        return ztYesTaskEntityMapper.findTaskestimateByUser(init(name,beginTime,endTime));
    }

    private ZtTaskestimateEntity init(String name,String beginTime,String endTime){
        ZtTaskestimateEntity ztTaskestimateEntity = new ZtTaskestimateEntity();
        ztTaskestimateEntity.setUserName(name);
        ztTaskestimateEntity.setBeginTime(beginTime);
        ztTaskestimateEntity.setEndTime(endTime);
        return ztTaskestimateEntity;
    }
}
