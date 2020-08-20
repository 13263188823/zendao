package org.project.modules.zentao.service.impl;

import org.project.modules.zentao.entity.ZtWorkhistory;
import org.project.modules.zentao.mapper.ZtWorkhistoryMapper;
import org.project.modules.zentao.service.IZtWorkhistoryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 工时补录
 */
@Service
public class ZtWorkhistoryServiceImpl extends ServiceImpl<ZtWorkhistoryMapper, ZtWorkhistory> implements IZtWorkhistoryService {

    @Resource
    private ZtWorkhistoryMapper ztWorkhistoryMapper;

    @Override
    public List<ZtWorkhistory> findEntityAllByIds(String ids) {
        return ztWorkhistoryMapper.findEntityAllByIds(ids);
    }

    @Override
    public List<ZtWorkhistory> findEntityAll() {
        return ztWorkhistoryMapper.findEntityAll();
    }

    @Override
    public List<ZtWorkhistory> findByDate(String date,String yesDate) {
        return ztWorkhistoryMapper.findByDate(date,yesDate);
    }

    @Override
    public void deleteByUserNameAndcreateDate(String name, String date,String endTime) {
        ztWorkhistoryMapper.deleteByUserNameAndcreateDate(name,date,endTime);
    }
}
