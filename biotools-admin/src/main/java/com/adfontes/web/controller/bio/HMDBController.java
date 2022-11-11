package com.adfontes.web.controller.bio;

import com.adfontes.bio.domain.MetaboliteBean;
import com.adfontes.bio.service.impl.HMDBService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务器维护Controller
 *
 * @author adfontes
 * @date 2022-10-17
 */
@Controller
@RequestMapping("/bio/hmdb")
@Transactional
public class HMDBController extends BaseController
{
    private String prefix = "bio/hmdb";

    @Autowired
    private HMDBService hmdbService;


    @GetMapping()
    public String hmdb()
    {
        return prefix + "/hmdb";
    }


    @PostMapping("/list")
    @ResponseBody
    public AjaxResult list(String hmdbIds)
    {
        //hmdbIds = "HMDB0000001";
        Set ids = new HashSet<String>(Arrays.asList(hmdbIds.split(",")).stream().map(String::trim).collect(Collectors.toList()));
        return AjaxResult.success(hmdbService.findHMDBDataById(ids));
    }


    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(String hmdbIds)
    {
        Set ids = new HashSet<String>(Arrays.asList(hmdbIds.split(",")).stream().map(String::trim).collect(Collectors.toList()));
        ExcelUtil<MetaboliteBean> util = new ExcelUtil<MetaboliteBean>(MetaboliteBean.class);
        return util.exportExcel(hmdbService.findHMDBDataById(ids), "MetaboliteBean");

    }

}
