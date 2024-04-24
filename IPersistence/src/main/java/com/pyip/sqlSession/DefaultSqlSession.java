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
                String key = className + "." + methodName;
                MappedStatement mappedStatement =
                        configuration.getMappedStatementMap().get(key);
                Type genericReturnType = method.getGenericReturnType();
                ArrayList arrayList = new ArrayList<>();
                //判断是否实现泛型类型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(key, args);
                }
                return selectOne(key, args);
            }
        });
        return o;
    }
}
