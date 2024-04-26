package com.pyip.plug;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class,Integer.class})
})
public class MyPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("intercept ... object");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        System.out.println("plugin wrap MyPlugin");
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("set Properties: "+properties);
    }
}
