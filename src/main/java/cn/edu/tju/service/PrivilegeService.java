package cn.edu.tju.service;

import cn.edu.tju.entity.Privilege;

import java.util.List;

public interface PrivilegeService {

    List<Privilege> getPrivilegeByRoleid(int roleid);

    int addDefaultPrivilegesWhenAddRole(String roleid);

    int delPrivilegesWenDelRole(String roleid);
}
