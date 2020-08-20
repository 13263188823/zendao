package org.project.modules.zentao.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.project.common.api.vo.Result;
import org.project.common.aspect.annotation.AutoLog;
import org.project.common.aspect.annotation.PermissionData;
import org.project.common.constant.CommonConstant;
import org.project.common.system.base.controller.BaseController;
import org.project.common.system.query.QueryGenerator;
import org.project.common.util.DateUtils;
import org.project.common.util.RedisUtil;
import org.project.modules.dynamic.entity.DynamicEntity;
import org.project.modules.zentao.entity.ZentaoHisEntity;
import org.project.modules.zentao.service.IZentaoHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * 禅道工时补录接口
 */

@Slf4j
@Api(tags = "禅道工时补录接口")
@RestController
@RequestMapping("/zentao/ztWorkhistory1")
public class ZentaoController extends BaseController<ZentaoHisEntity, IZentaoHisService> {
    @Autowired
    private IZentaoHisService iZentaoViewService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页列表查询
     */
    @ApiOperation(value = "获取禅道补录信息列表", notes = "获取所有禅道补录信息列表")
    @GetMapping(value = "/list")
    public Result<?> list(ZentaoHisEntity zentaoHisEntity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {
        QueryWrapper<ZentaoHisEntity> queryWrapper = QueryGenerator.initQueryWrapper(zentaoHisEntity, req.getParameterMap());
        Page<ZentaoHisEntity> page = new Page<ZentaoHisEntity>(pageNo, pageSize);

        IPage<ZentaoHisEntity> pageList = iZentaoViewService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.ok(pageList);
    }

    /**
     * 添加
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加禅道补录信息")
    @ApiOperation(value = "添加禅道补录信息", notes = "添加禅道补录信息")
    public Result<?> add(@RequestBody ZentaoHisEntity zentaoHisEntity) {
        iZentaoViewService.save(zentaoHisEntity);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     */
    @PutMapping(value = "/edit")
    @ApiOperation(value = "编辑禅道补录信息", notes = "编辑禅道补录信息")
    @AutoLog(value = "修改补录信息", operateType = CommonConstant.OPERATE_TYPE_3)
    public Result<?> edit(@RequestBody ZentaoHisEntity zentaoHisEntity) {
        iZentaoViewService.updateById(zentaoHisEntity);
        return Result.ok("更新成功！");
    }

    /**
     * 通过id删除
     */
    @AutoLog(value = "删除禅道补录信息")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "通过ID删除补录信息", notes = "通过ID删除补录信息")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        iZentaoViewService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     */
    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除补录信息", notes = "批量删除补录信息")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.iZentaoViewService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     */
    @GetMapping(value = "/queryById")
    @ApiOperation(value = "通过ID查询补录信息", notes = "通过ID查询补录信息")
    public Result<?> queryById(@ApiParam(name = "id", value = "示例id", required = true) @RequestParam(name = "id", required = true) String id) {
        ZentaoHisEntity zentaoHisEntity = iZentaoViewService.getById(id);
        return Result.ok(zentaoHisEntity);
    }

    /**
     * 导出excel
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZentaoHisEntity zentaoHisEntity) {
        return super.exportXls(request, zentaoHisEntity, ZentaoHisEntity.class, "单表模型");
    }

    /**
     * 通过excel导入数据
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZentaoHisEntity.class);
    }

    // =====Redis 示例===============================================================================================

    /**
     * redis操作 -- set
     */
    @GetMapping(value = "/redisSet")
    public void redisSet() {
        redisUtil.set("name", "张三" + DateUtils.now());
    }

    /**
     * redis操作 -- get
     */
    @GetMapping(value = "/redisGet")
    public String redisGet() {
        return (String) redisUtil.get("name");
    }

    /**
     * redis操作 -- setObj
     */
    /*@GetMapping(value = "/redisSetObj")
    public void redisSetObj() {
        JeecgDemo p = new JeecgDemo();
        p.setAge(10);
        p.setBirthday(new Date());
        p.setContent("hello");
        p.setName("张三");
        p.setSex("男");
        redisUtil.set("user-zdh", p);
    }*/

    /**
     * redis操作 -- setObj
     */
    @GetMapping(value = "/redisGetObj")
    public Object redisGetObj() {
        return redisUtil.get("user-zdh");
    }

    /**
     * redis操作 -- get
     */
   /* @GetMapping(value = "/redis/{id}")
    public JeecgDemo redisGetJeecgDemo(@PathVariable("id") String id) {
        JeecgDemo t = jeecgDemoService.getByIdCacheable(id);
        log.info(t.toString());
        return t;
    }*/

    /*----------------------------------------外部获取权限示例------------------------------------*/

    public static void main(String[] args) {
        DynamicEntity dynamicEntity =new DynamicEntity();
        dynamicEntity.setSqlvalue("select * from aaaa");
        dynamicEntity.setStatus("22");
        dynamicEntity.setTitle("33");
        JSONObject json = (JSONObject) JSONObject.toJSON(dynamicEntity);
        System.out.println(json.toJSONString());
    }

}
