package org.project.modules.zentao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.project.modules.zentao.entity.ZtWorkhistory;

/**
 * @Description: 工时补录
 */
public interface ZtWorkhistoryMapper extends BaseMapper<ZtWorkhistory> {
    public List<ZtWorkhistory> findEntityAllByIds(String ids);
    public List<ZtWorkhistory> findEntityAll();
    public List<ZtWorkhistory> findByDate(String date, String yesDate);
    public void deleteByUserNameAndcreateDate(String name, String date, String endTime);
}
