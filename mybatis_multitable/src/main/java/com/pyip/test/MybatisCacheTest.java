package com.pyip.test;

import com.pyip.mapper.IOrderMapper;
import com.pyip.mapper.IUserMapper;
import com.pyip.pojo.User;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Logger
public class MybatisCacheTest {
    SqlSession sqlSession;
    SqlSessionFactory sqlSessionFactory;
    IUserMapper userMapper;
    IOrderMapper orderMapper;
    @Before
    public void bfr() throws IOException {
        InputStream resourceAsStream =
                Resources.getResourceAsStream("SqlMapConfig.xml");
         sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(resourceAsStream);
         sqlSession = sqlSessionFactory.openSession(true);
    }
    @Test
    public void test1() throws IOException {
        //根据 sqlSessionFactory 产⽣ session
        userMapper = sqlSession.getMapper(IUserMapper.class);
        //第⼀次查询，发出sql语句，并将查询出来的结果放进缓存中
        User u1 = userMapper.selectUserByIdWithAnnotation(1);
        System.out.println(u1);
        //第⼆次查询，由于是同⼀个sqlSession,会在缓存中查询结果
        //如果有，则直接从缓存中取出来，不和数据库进⾏交互
        User u2 = userMapper.selectUserByIdWithAnnotation(1);
        System.out.println(u2);
        System.out.println(u1 == u2);
        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {
        //根据 sqlSessionFactory 产⽣ session
        userMapper = sqlSession.getMapper(IUserMapper.class);
        //第⼀次查询，发出sql语句，并将查询出来的结果放进缓存中
        User u1 = userMapper.selectUserByIdWithAnnotation(1);
        System.out.println(u1);
        userMapper.update(u1);
        sqlSession.commit();
        //第⼆次查询，由于是同⼀个sqlSession,会在缓存中查询结果
        //如果有，则直接从缓存中取出来，不和数据库进⾏交互
        User u2 = userMapper.selectUserByIdWithAnnotation(1);
        System.out.println(u2);
        System.out.println(u1 == u2);
        sqlSession.close();
    }

    @Test
    public void test3(){
        //根据 sqlSessionFactory 产⽣ session
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        IUserMapper userMapper1 = sqlSession1.getMapper(IUserMapper. class );
        IUserMapper userMapper2 = sqlSession2.getMapper(IUserMapper. class );
        //第⼀次查询，发出sql语句，并将查询的结果放⼊缓存中
        User u1 = userMapper1.selectUserByIdWithAnnotation(1);
        System.out.println(u1);
        sqlSession1.close(); //第⼀次查询完后关闭 sqlSession

        //第⼆次查询，即使sqlSession1已经关闭了，这次查询依然不发出sql语句
        User u2 = userMapper2.selectUserByIdWithAnnotation(1);
        System.out.println(u2);
        System.out.println(u1 == u2);
        sqlSession2.close();
    }
}
