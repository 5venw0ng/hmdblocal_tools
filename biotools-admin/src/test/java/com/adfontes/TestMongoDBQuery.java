package com.adfontes;

import com.adfontes.bio.domain.MetaboliteBean;
import com.adfontes.bio.service.impl.HMDBService;
import com.adfontes.dbUnit.AbstractDBUnitTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 拼团订单的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestMongoDBQuery extends AbstractDBUnitTestCase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HMDBService hmdbService;

    @Test
    public void testStartGroupBuy() {
        List<MetaboliteBean> maps = hmdbService.findHMDBDataById(Set.of("HMDB0000001"));

    }

}