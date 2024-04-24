package com.pyip.test;

import com.pyip.dao.IUser;
import com.pyip.io.Resource;
import com.pyip.pojo.User;
import com.pyip.sqlSession.SQLSessionFactory;
import com.pyip.sqlSession.SqlSession;
import com.pyip.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resource.getResourceAsStream("sqlMapConfig.xml");
        SQLSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        User user = new User();
        user.setId("1");
        user.setUsername("张三");
//        List<Object> user2 = sqlSession.selectList("user.selectOne", user);
//        User user2 = sqlSession.selectOne("user.selectOne", user);
//        System.out.println(user2);
//        List<User> objects = sqlSession.selectList("user.selectAll");
//        for (User object : objects) {
//            System.out.println(object);
//        }

        IUser mapper = sqlSession.getMapper(IUser.class);
        for (User user1 : mapper.selectAll()) {
            System.out.println(user1);
        }
        User user1 = mapper.selectOne(user);
        System.out.println(user1);

    }
}
