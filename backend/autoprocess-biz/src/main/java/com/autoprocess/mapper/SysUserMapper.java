package com.autoprocess.mapper;

import com.autoprocess.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysUserMapper {

    SysUser selectByUsername(@Param("username") String username);
    SysUser selectById(@Param("id") String id);

    List<SysUser> selectList();

    int insert(SysUser sysUser);

    int updateStatus(SysUser sysUser);
}
