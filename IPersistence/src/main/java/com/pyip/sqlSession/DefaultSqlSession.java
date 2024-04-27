package com.pyip.sqlSession;

import com.pyip.pojo.Configuration;
import com.pyip.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
//        将要去完成对simpleExecutor里的query方法的调用
        Executor simpleExcutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list = simpleExcutor.query(configuration, mappedStatement, params);

        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1){
            return (T) objects.get(0);
        }else{
            throw new RuntimeException("查询结果不正确");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        T o = (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new
                Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                // selectOne
                String methodName = method.getName();
                // className:namespace
                String className = method.getDeclaringClass().getName();
                //statementid
                String statementId = className + "." + methodName;

                // 准备参数2：params:args
                // 获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了 泛型类型参数化
                if(genericReturnType instanceof ParameterizedType){
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }

                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

                String sql = mappedStatement.getSql();
                if(sql.startsWith("update")){
                    return update(statementId,args);
                }
                if(sql.startsWith("delete")){
                    return delete(statementId,args);
                }
                return selectOne(statementId,args);
            }
        });
        return o;
    }

    @Override
    public Integer update(String statementid, Object... params) throws Exception {
        Executor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        Integer i = simpleExecutor.update(configuration, mappedStatement, params);
        return i;
    }

    @Override
    public Integer delete(String statementid, Object... params) throws Exception {
        Executor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        Integer i = simpleExecutor.delete(configuration, mappedStatement, params);
        return i;

    }
}
