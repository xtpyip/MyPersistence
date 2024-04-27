package com.pyip.test;

import com.pyip.dao.IUserDao;
import com.pyip.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
//import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    @Test
    public void test01() throws IOException {
        //1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2.解析了配置文件，并创建了sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //3.生产sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();// 默认开启一个事务，但是该事务不会自动提交
        //在进行增删改操作时，要手动提交事务
        //4.sqlSession调用方法：查询所有selectList  查询单个：selectOne 添加：insert  修改：update 删除：delete
//        List<User> users = sqlSession.selectList("user.findAll");
        List<User> users = sqlSession.selectList("com.pyip.dao.IUserDao.findAll");
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);//事务自动提交
        User user = new User();
        user.setId(6);
        user.setUsername("tom");
//        sqlSession.insert("user.saveUser",user);
        sqlSession.insert("com.pyip.dao.IUserDao.saveUser",user);
        sqlSession.close();
    }

    @Test
    public void test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(2);
        user.setUsername("lucy");
//        sqlSession.update("user.updateUser",user);
        sqlSession.update("com.pyip.dao.IUserDao.updateUser",user);
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void test4() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.pyip.dao.IUserDao.deleteUser",6);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void test5() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }


    }

    @Test
    public void test6() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(2);
        user1.setUsername("lucy");

        List<User> all = mapper.findByCondition(user1);
        for (User user : all) {
            System.out.println(user);
        }


    }

    @Test
    public void test7() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        int[] arr = {1, 2};

        List<User> all = mapper.findByIds(arr);
        for (User user : all) {
            System.out.println(user);
        }

    }
}
