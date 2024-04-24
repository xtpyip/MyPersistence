package com.pyip.sqlSession;

import com.pyip.config.XMLConfigBuilder;
import com.pyip.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SQLSessionFactory build(InputStream in) throws Exception {
        // 第一：使用dom4j解析配置文件，将解析出来的内容封装到Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

// 第二：创建sqlSessionFactory对象 工厂对象，生产sqlSession 会话对像
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;

    }








}
