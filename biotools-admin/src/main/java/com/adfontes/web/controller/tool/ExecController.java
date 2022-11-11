package com.adfontes.web.controller.tool;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.quartz.util.JobInvokeUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * build 表单构建
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/tool/executefunc")
@Transactional
public class ExecController extends BaseController
{
    private String prefix = "tool/exec";

    @RequiresPermissions("tool:exec:view")
    @GetMapping()
    public String exec()
    {
        return prefix + "/exec";
    }

    @RequiresPermissions("tool:exec:view")
    @PostMapping("/exec")
    @ResponseBody
    public AjaxResult execFunction(@RequestParam String invokeTarget){
        try {
            JobInvokeUtil.invokeMethod(invokeTarget);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success();
    }
}
