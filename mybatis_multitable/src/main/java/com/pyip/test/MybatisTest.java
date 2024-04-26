package com.pyip.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyip.mapper.IOrderMapper;
import com.pyip.mapper.IUserMapper;
import com.pyip.mapper.UserMapper;
import com.pyip.pojo.Order;
import com.pyip.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MybatisTest {
//    测试一对一
    @Test
    public void test1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IOrderMapper mapper = sqlSession.getMapper(IOrderMapper.class);
        List<Order> orderAndUser = mapper.findOrderAndUser();
        for (Order order : orderAndUser) {
            System.out.println(order);
        }
    }

//    测试一对多
    @Test
    public void test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> users    = mapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    //    测试多对多
    @Test
    public void test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        List<User> users    = mapper.findAllUserAndRole();
        for (User user : users) {
            System.out.println(user);
        }
    }
    private SqlSessionFactory sqlSessionFactory;
    private IUserMapper userMapper;
    private IOrderMapper orderMapper;
    @Before
    public void before() throws IOException {
        InputStream resourceAsStream =
                Resources.getResourceAsStream("SqlMapConfig.xml");
         sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(IUserMapper.class);
        orderMapper = sqlSession.getMapper(IOrderMapper.class);
    }

    @Test
    public void test4(){
        User user = new User();
        user.setId(6);
        user.setUsername("jjj");
        userMapper.add(user);
    }
    @Test
    public void test5(){
        User user = new User();
        user.setId(6);
        user.setUsername("aaa");
        userMapper.update(user);
    }
    @Test
    public void test6(){
        User user = new User();
        user.setId(6);
        User byId = userMapper.findById(user);
        System.out.println(byId);
    }
    @Test
    public void test7(){
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void test8(){
        User user = new User();
        user.setId(6);
        userMapper.delete(user);
    }

    // 注解一对一
    @Test
    public void test9(){
        List<Order> orders = orderMapper.findAllWithAnnotation();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    // 注解一对多
    @Test
    public void test10(){
        for (User user : userMapper.findAllUserAndOrderWithAnnotation()) {
            System.out.println(user);
        }

    }
    // 注解多对多
    @Test
    public void test11(){
        for (User user : userMapper.findAllUserAndRoleWithAnnotation()) {
            System.out.println(user);
        }

    }

    @Test
    public void testPageHelper() {
        //设置分⻚参数
        PageHelper.startPage(1, 2);
        List<User> select = userMapper.findAllUserAndOrderWithAnnotation();
        for (User user : select) {
            System.out.println(user);
        }
        //其他分⻚的数据
        PageInfo<User> pageInfo = new PageInfo<User>(select);
        System.out.println("总条数："+pageInfo.getTotal());
        System.out.println("总⻚数："+pageInfo. getPages ());
        System.out.println("当前⻚："+pageInfo. getPageNum());
        System.out.println("每⻚显万⻓度："+pageInfo.getPageSize());
        System.out.println("是否第⼀⻚："+pageInfo.isIsFirstPage());
        System.out.println("是否最后⼀⻚："+pageInfo.isIsLastPage());
    }

    @Test
    public void test12() throws IOException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        //(1)mapper基础接⼝
        //select 接⼝
        User user1 = userMapper.selectOne(user); //根据实体中的属性进⾏查询，只能有—个返回值
        List<User> users = userMapper.select(null); //查询全部结果
        userMapper.selectByPrimaryKey(10); //根据主键字段进⾏查询，⽅法参数必须包含完整的主键属性，查询条件使⽤等号
        userMapper.selectCount(user); //根据实体中的属性查询总数，查询条件使⽤等号
//        // insert 接⼝
//        int insert = userMapper.insert(user); //保存⼀个实体，null值也会保存，不会使⽤数据库默认值
//        int i = userMapper.insertSelective(user); //保存实体，null的属性不会保存，会使⽤数据库默认值
//        // update 接⼝
//        int i1 = userMapper.updateByPrimaryKey(user);//根据主键更新实体全部字段，null值会被更新
//        // delete 接⼝
//        int delete = userMapper.delete(user); //根据实体属性作为条件进⾏删除，查询条件 使⽤等号
//        userMapper.deleteByPrimaryKey(1); //根据主键字段进⾏删除，⽅法参数必须包含完整的主键属性
        //(2)example⽅法
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", 1);
        example.createCriteria().andLike("username", "c");
        //⾃定义查询
        List<User> users1 = userMapper.selectByExample(example);
        for (User user2 : users1) {
            System.out.println("1:"+user2);
        }

    }
}
