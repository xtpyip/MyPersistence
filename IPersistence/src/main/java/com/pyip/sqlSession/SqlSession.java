package com.pyip.sqlSession;

import java.util.List;

public interface SqlSession {
    // 查询所有
    public <E> List<E> selectList(String statementId, Object...params) throws Exception;

    // 根据条件查询单个
    public <T> T selectOne(String statementId, Object...params) throws Exception;

    public <T> T getMapper(Class<?> mapperClass);

    //根据条件进行修改
    public Integer update(String statementid,Object... params) throws Exception;
    //根据条件进行删除
    public Integer delete(String statementid,Object... params) throws Exception;

}
