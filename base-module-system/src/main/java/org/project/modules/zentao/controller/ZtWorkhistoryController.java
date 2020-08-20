package org.project.modules.zentao.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.project.common.api.vo.Result;
import org.project.common.aspect.annotation.AutoLog;
import org.project.common.system.base.controller.BaseController;
import org.project.common.system.query.QueryGenerator;
import org.project.modules.message.entity.ZtWorkHistoryEntity;
import org.project.modules.message.service.IZentaoService;
import org.project.modules.zentao.entity.ZtWorkhistory;
import org.project.modules.zentao.service.IZtWorkhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 工时补录
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
@Api(tags="工时补录")
@RestController
@RequestMapping("/zentao/ztWorkhistory")
@Slf4j
public class ZtWorkhistoryController extends BaseController<ZtWorkhistory, IZtWorkhistoryService> {
	@Autowired
	private IZtWorkhistoryService ztWorkhistoryService;

	@Resource
    private IZentaoService izentaoService;

	/**
	 * 分页列表查询
	 *
	 * @param ztWorkhistory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "工时补录-分页列表查询")
	@ApiOperation(value="工时补录-分页列表查询", notes="工时补录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZtWorkhistory ztWorkhistory,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZtWorkhistory> queryWrapper = QueryGenerator.initQueryWrapper(ztWorkhistory, req.getParameterMap());
		Page<ZtWorkhistory> page = new Page<ZtWorkhistory>(pageNo, pageSize);
		IPage<ZtWorkhistory> pageList = ztWorkhistoryService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ztWorkhistory
	 * @return
	 */
	@AutoLog(value = "工时补录-添加")
	@ApiOperation(value="工时补录-添加", notes="工时补录-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZtWorkhistory ztWorkhistory) {
		ztWorkhistoryService.save(ztWorkhistory);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ztWorkhistory
	 * @return
	 */
	@AutoLog(value = "工时补录-编辑")
	@ApiOperation(value="工时补录-编辑", notes="工时补录-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZtWorkhistory ztWorkhistory) {
		ztWorkhistoryService.updateById(ztWorkhistory);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "工时补录-通过id删除")
	@ApiOperation(value="工时补录-通过id删除", notes="工时补录-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
	    //这里影响性能,需注意优化
		//补录工时 删除同时从新补录信息
        ZtWorkhistory ztWorkhistory = ztWorkhistoryService.getById(id);
        //memo内容中起始的同步ID
        //这里需要优化会影响性能
		StringBuffer strB = new StringBuffer();
		List<ZtWorkHistoryEntity> listZwhe = izentaoService.findEntityAll();
		//假设用于批量,节省工时
        for(ZtWorkHistoryEntity whe :listZwhe){
            if(whe.getMemo().equals(ztWorkhistory.getMemo()))
				strB.append(whe.getId());
        }
		izentaoService.updateEntity(strB.toString());
        ztWorkhistoryService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "工时补录-批量删除")
	@ApiOperation(value="工时补录-批量删除", notes="工时补录-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {

		//这里影响性能,需注意优化
		//补录工时 删除同时从新补录信息
		//Arrays.asList(ids.split(",")) 十分消耗性能,
		// 应传输ids 因为页面可能调整暂时这样处理 后续调整
		//十分消耗,性能非常差待优化(ids)

		for(String s :Arrays.asList(ids.split(","))){
			ZtWorkhistory ztWorkhistory = ztWorkhistoryService.getById(s);
			//memo内容中起始的同步ID
			//这里需要优化会影响性能
			StringBuffer strB = new StringBuffer();
			List<ZtWorkHistoryEntity> listZwhe = izentaoService.findEntityAll();
			//假设用于批量,节省工时
			for(ZtWorkHistoryEntity whe :listZwhe){
				if(whe.getMemo().equals(ztWorkhistory.getMemo()))
					strB.append(whe.getId());
			}
			izentaoService.updateEntity(strB.toString());
		}
		this.ztWorkhistoryService.removeByIds(Arrays.asList(ids.split(",")));

		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "工时补录-通过id查询")
	@ApiOperation(value="工时补录-通过id查询", notes="工时补录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZtWorkhistory ztWorkhistory = ztWorkhistoryService.getById(id);
		if(ztWorkhistory==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(ztWorkhistory);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ztWorkhistory
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZtWorkhistory ztWorkhistory) {
        return super.exportXls(request, ztWorkhistory, ZtWorkhistory.class, "工时补录");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZtWorkhistory.class);
    }


}
