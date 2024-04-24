package com.pyip.sqlSession;

import com.pyip.pojo.Configuration;

public class DefaultSqlSessionFactory implements SQLSessionFactory {
    private Configuration configuration;
    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
