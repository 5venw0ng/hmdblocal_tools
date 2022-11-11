package com.ruoyi.system.service.impl;

import com.adfontes.common.services.impl.GenericServicesImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TagsMapper;
import com.ruoyi.system.domain.Tags;
import com.ruoyi.system.service.ITagsService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 标签 服务层实现
 *
 * @author adfontes
 * @date 2021-04-27
 */
@Service
@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=RuntimeException.class)
public class TagsServiceImpl extends GenericServicesImpl<TagsMapper, Tags> implements ITagsService
{

}