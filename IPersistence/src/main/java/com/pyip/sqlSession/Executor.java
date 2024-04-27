package com.pyip.sqlSession;

import com.pyip.pojo.Configuration;
import com.pyip.pojo.MappedStatement;

import java.util.List;

public interface Executor {
     public <E> List<E> query(Configuration configuration, MappedStatement
                mappedStatement, Object...params) throws Exception;

    public Integer update(Configuration configuration,
                          MappedStatement mappedStatement, Object[] params) throws Exception;
    public Integer delete(Configuration configuration,
                          MappedStatement mappedStatement, Object[] params) throws Exception;
}
