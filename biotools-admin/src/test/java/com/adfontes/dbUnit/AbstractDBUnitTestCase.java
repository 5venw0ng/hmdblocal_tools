package com.adfontes.dbUnit;


import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Service
public class AbstractDBUnitTestCase {

        private DatabaseConnection conn;   //这个不是真正的数据库的连接的  封装


        @Autowired
        private DataSource dataSource;

        /**
         * 这个方法的作用就是初始化上面的DatabaseConnection
         */
        public void setConn() throws DatabaseUnitException {
            conn=new MySqlConnection(DataSourceUtils.getConnection(dataSource),"adfontes");
            //conn.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        }

        /**
         * 插入测试数据
         */
        @Transactional
        public void insertTestData(IDataSet ds) throws DatabaseUnitException, SQLException {
            DatabaseOperation.REFRESH.execute(conn,ds);
            //DatabaseOperation.TRANSACTION(DatabaseOperation.REFRESH).execute(conn,ds);
            //SpringUtils.getBean(MyTransactionOperation.class).setDO(DatabaseOperation.INSERT);
            //SpringUtils.getBean(MyTransactionOperation.class).execute(conn,ds); //使用回滚的事务
        }


        /**
         * 还原表的数据
         */
        public void resumeTable(IDataSet ds) throws DatabaseUnitException, SQLException, FileNotFoundException {
            //IDataSet dataSet=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
            DatabaseOperation.DELETE.execute(conn,ds);
        }





}
