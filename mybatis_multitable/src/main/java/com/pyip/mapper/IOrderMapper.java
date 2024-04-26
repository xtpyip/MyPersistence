package com.pyip.mapper;

import com.pyip.pojo.Order;
import com.pyip.pojo.User;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IOrderMapper {
    public List<Order> findOrderAndUser();

    @Select("select * from orders")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "total",column = "total"),
            @Result(property = "user",column = "uid",
                    javaType = User.class, one = @One(select = "com.pyip.mapper.IUserMapper." +
                    "selectUserByIdWithAnnotation"))

    })
    public List<Order> findAllWithAnnotation();

    @Select("select * from orders where id = #{id}")
    public List<Order> selectOrderByIdWithAnnotation(Integer id);
}
