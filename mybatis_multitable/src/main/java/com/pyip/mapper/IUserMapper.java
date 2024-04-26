package com.pyip.mapper;

import com.pyip.pojo.Order;
import com.pyip.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;
// 基于注解的二级缓存开启
//@CacheNamespace(implementation = PerpetualCache.class) // 默认二级缓存实现为PerpetualCache
@CacheNamespace(implementation = RedisCache.class)
public interface IUserMapper {
    public List<User> findAll();

    List<User> findAllUserAndRole();

    @Insert("insert into user values(#{id},#{username},#{password},now())")
    public void add(User user);

    @Update("update user set username = #{username} where id = #{id}")
    public void update(User user);

    @Delete("delete from user where id = #{id}")
    public void delete(User user);

    @Select("select * from user where id = #{id}")
    public User findById(User user);

    @Select("select * from user")
    public List<User> selectAll();

//    @Options(useCache = false) // 禁用此方法的二级缓存，默认为true
    @Select("select * from user where id = #{id}")
    public User selectUserByIdWithAnnotation(Integer id);


    @Select("select * from user")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "orderList",column = "id",
            javaType = List.class,
            many = @Many(select = "com.pyip.mapper.IOrderMapper." +
                    "selectOrderByIdWithAnnotation"))
    })
    public List<User> findAllUserAndOrderWithAnnotation();


    @Select("select * from user")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "roleList",column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.pyip.mapper.IRoleMapper." +
                            "selectRoleByIdWithAnnotation"))
    })
    public List<User> findAllUserAndRoleWithAnnotation();

}
