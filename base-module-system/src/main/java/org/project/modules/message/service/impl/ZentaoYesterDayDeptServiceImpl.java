package org.project.modules.message.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.project.common.system.base.service.impl.BaseServiceImpl;
import org.project.modules.message.entity.DeptEntity;
import org.project.modules.message.entity.UserEntity;
import org.project.modules.message.mapper.ZantaoYesterDeptMapper;
import org.project.modules.message.mapper.ZantaoYesterMapper;
import org.project.modules.message.service.IZentaoYesterDayDeptService;
import org.project.modules.message.service.IZentaoYesterDayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
@DS("multi-datasource2")
public class ZentaoYesterDayDeptServiceImpl extends BaseServiceImpl<ZantaoYesterDeptMapper,DeptEntity> implements IZentaoYesterDayDeptService {

    @Resource
    private ZantaoYesterDeptMapper ztYesDeptMapper;

    @Override
    public List<DeptEntity> findDeptAll() {
        return ztYesDeptMapper.findDeptAll();
    }
}
