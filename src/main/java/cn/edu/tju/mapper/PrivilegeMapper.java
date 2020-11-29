package cn.edu.tju.mapper;

import cn.edu.tju.entity.Privilege;

import java.util.List;

public interface PrivilegeMapper {

    List<Privilege> getPrivilegeByRoleid(int roleid);

    int addDefaultPrivilegesWhenAddRole(String roleid);

    int delPrivilegesWenDelRole(String roleid);
}
