package com.pyip.mapper;

import com.pyip.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IRoleMapper {

    @Select("select * from sys_role r,sys_user_role ur where r.id=ur.roleid and ur.userid=#{uid}")
    public List<Role> selectRoleByIdWithAnnotation(int uid);
}
