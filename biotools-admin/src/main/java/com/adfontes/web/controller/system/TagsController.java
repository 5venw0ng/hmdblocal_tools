package com.adfontes.web.controller.system;

import java.util.List;
import java.util.Arrays;

import com.adfontes.common.services.impl.GenericQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.text.Convert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Tags;
import com.ruoyi.system.service.ITagsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标签Controller
 * 
 * @author adfontes
 * @date 2021-04-27
 */
@Controller
@RequestMapping("/system/tags")
@Transactional
public class TagsController extends BaseController
{
    private String prefix = "system/tags";

    @Autowired
    private ITagsService tagsService;

    @RequiresPermissions("system:tags:view")
    @GetMapping()
    public String tags()
    {
        return prefix + "/tags";
    }

    /**
     * 查询标签列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Tags tags)
    {
        startPage();
        return getDataTable(tagsService.list(new QueryWrapper<Tags>(tags)));
    }

    @GetMapping("/query")
    @ResponseBody
    public TableDataInfo query(String q)
    {
        return getDataTable(tagsService.list(new GenericQueryWrapper<Tags>().like("tagName",q)));
    }

    /**
     * 导出标签列表
     */
    @RequiresPermissions("system:tags:view")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Tags tags)
    {
        List<Tags> list = tagsService.list(new QueryWrapper<>());
        ExcelUtil<Tags> util = new ExcelUtil<Tags>(Tags.class);
        return util.exportExcel(list, "tags");
    }

    /**
     * 新增标签
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存标签
     */
    @RequiresPermissions("system:tags:edit")
    @Log(title = "标签", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Tags tags)
    {
        return toAjax(tagsService.save(tags));
    }

    /**
     * 修改标签
     */
    @GetMapping("/edit/{tagId}")
    public String edit(@PathVariable("tagId") Long tagId, ModelMap mmap)
    {
        Tags tags = tagsService.getById(tagId);
        mmap.put("tags", tags);
        return prefix + "/edit";
    }

    /**
     * 修改保存标签
     */
    @RequiresPermissions("system:tags:edit")
    @Log(title = "标签", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Tags tags)
    {
        return toAjax(tagsService.updateById(tags));
    }

    /**
     * 删除标签
     */
    @RequiresPermissions("system:tags:edit")
    @Log(title = "标签", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tagsService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }
}
