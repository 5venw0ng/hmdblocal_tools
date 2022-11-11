package com.ruoyi.common.utils.html;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("commonService")
public class HtmlCommonService {

    public static BigDecimal validAndConvtToBigDecimal(String str){
        BigDecimal b = BigDecimal.ZERO;
        if(StringUtils.isNumeric(str)){
            System.out.println(str);
            return new BigDecimal(str);
        }
        return b;
    }
}
