package org.project.modules.zentao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.project.modules.zentao.entity.ZtWorkhistory;

import java.util.List;

/**
 * @Description: 工时补录
 */
public interface IZtWorkhistoryService extends IService<ZtWorkhistory> {
    public List<ZtWorkhistory> findEntityAllByIds(String str);
    public List<ZtWorkhistory> findEntityAll();
    public List<ZtWorkhistory> findByDate(String date, String yesDate);
    public void deleteByUserNameAndcreateDate(String name, String date, String endTime);
}
