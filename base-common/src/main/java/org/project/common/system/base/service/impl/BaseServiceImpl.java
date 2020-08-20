package org.project.common.system.base.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.project.common.system.base.entity.BaseEntity;
import org.project.common.system.base.service.BaseService;


/**
 * mybatis-pluse 基类
 * @param <M>
 * @param <T>
 */

@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

}
