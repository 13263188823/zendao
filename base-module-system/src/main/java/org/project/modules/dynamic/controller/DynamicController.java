package org.project.modules.dynamic.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
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
import org.project.common.system.base.controller.JeecgController;
import org.project.common.system.query.QueryGenerator;
import org.project.common.util.DateUtils;
import org.project.common.util.RedisUtil;
import org.project.modules.demo.test.entity.JeecgDemo;
import org.project.modules.demo.test.service.IJeecgDemoService;
import org.project.modules.dynamic.entity.DynamicEntity;
import org.project.modules.dynamic.service.IDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * 动态SQL接口
 */

@Slf4j
@Api(tags = "动态SQL接口")
@RestController
@RequestMapping("/Dynamic/dynamic")
public class DynamicController extends BaseController<DynamicEntity, IDynamicService> {
    @Autowired
    private IDynamicService iDynamicService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 分页列表查询
     *
     * @param jeecgDemo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取动态SQL信息列表", notes = "获取所有动态SQL信息列表")
    @GetMapping(value = "/list")
    @PermissionData(pageComponent = "Dynamic/dynamic")
    public Result<?> list(DynamicEntity dynamicEntity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {
        QueryWrapper<DynamicEntity> queryWrapper = QueryGenerator.initQueryWrapper(dynamicEntity, req.getParameterMap());
        Page<DynamicEntity> page = new Page<DynamicEntity>(pageNo, pageSize);

        IPage<DynamicEntity> pageList = iDynamicService.page(page, queryWrapper);
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
    @AutoLog(value = "添加动态SQL信息")
    @ApiOperation(value = "添加动态SQL信息", notes = "添加动态SQL信息")
    public Result<?> add(@RequestBody DynamicEntity dynamicEntity) {
        iDynamicService.save(dynamicEntity);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     */
    @PutMapping(value = "/edit")
    @ApiOperation(value = "编辑动态SQL信息", notes = "编辑动态SQL信息")
    @AutoLog(value = "编辑DEMO", operateType = CommonConstant.OPERATE_TYPE_3)
    public Result<?> edit(@RequestBody DynamicEntity dynamicEntity) {
        iDynamicService.updateById(dynamicEntity);
        return Result.ok("更新成功！");
    }

    /**
     * 通过id删除
     */
    @AutoLog(value = "删除动态SQL信息")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "通过ID删除动态SQL信息", notes = "通过ID删除动态SQL信息")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        iDynamicService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     */
    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除DEMO", notes = "批量删除DEMO")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.iDynamicService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     */
    @GetMapping(value = "/queryById")
    @ApiOperation(value = "通过ID查询DEMO", notes = "通过ID查询DEMO")
    public Result<?> queryById(@ApiParam(name = "id", value = "示例id", required = true) @RequestParam(name = "id", required = true) String id) {
        DynamicEntity dynamicEntity = iDynamicService.getById(id);
        return Result.ok(dynamicEntity);
    }

    /**
     * 导出excel
     */
    @RequestMapping(value = "/exportXls")
    @PermissionData(pageComponent = "jeecg/JeecgDemoList")
    public ModelAndView exportXls(HttpServletRequest request, DynamicEntity dynamicEntity) {
        return super.exportXls(request, dynamicEntity, DynamicEntity.class, "单表模型");
    }

    /**
     * 通过excel导入数据
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DynamicEntity.class);
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

    // ===Freemaker示例================================================================================

    /**
     * freemaker方式 【页面路径： src/main/resources/templates】
     *
     * @param modelAndView
     * @return
     */
 /*   @RequestMapping("/html")
    public ModelAndView ftl(ModelAndView modelAndView) {
        modelAndView.setViewName("demo3");
        List<String> userList = new ArrayList<String>();
        userList.add("admin");
        userList.add("user1");
        userList.add("user2");
        log.info("--------------test--------------");
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }*/


    // ==========================================动态表单 JSON接收测试===========================================
    @PostMapping(value = "/testOnlineAdd")
    public Result<?> testOnlineAdd(@RequestBody JSONObject json) {
        log.info(json.toJSONString());
        return Result.ok("添加成功！");
    }

    /*----------------------------------------外部获取权限示例------------------------------------*/

    /**
     * 【数据权限示例 - 编程】mybatisPlus java类方式加载权限
     *
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/mpList")
    @PermissionData(pageComponent = "jeecg/JeecgDemoList")
    public Result<?> loadMpPermissonList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                         HttpServletRequest req) {
        QueryWrapper<DynamicEntity> queryWrapper = new QueryWrapper<DynamicEntity>();
        //编程方式，给queryWrapper装载数据权限规则
        QueryGenerator.installAuthMplus(queryWrapper, JeecgDemo.class);
        Page<DynamicEntity> page = new Page<DynamicEntity>(pageNo, pageSize);
        IPage<DynamicEntity> pageList = iDynamicService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 【数据权限示例 - 编程】mybatis xml方式加载权限
     *
     * @param jeecgDemo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/sqlList")
    @PermissionData(pageComponent = "jeecg/JeecgDemoList")
    public Result<?> loadSqlPermissonList(DynamicEntity dynamicEntity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                          HttpServletRequest req) {
        IPage<DynamicEntity> pageList = iDynamicService.queryListWithPermission(pageSize, pageNo);
        return Result.ok(pageList);
    }
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
