package cn.edu.tju.service.impl;

import cn.edu.tju.service.PrivilegeService;
import cn.edu.tju.mapper.PrivilegeMapper;
import cn.edu.tju.entity.Privilege;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Resource
    private PrivilegeMapper mapper;

    public List<Privilege> getPrivilegeByRoleid(int roleid) {
        return this.mapper.getPrivilegeByRoleid(roleid);
    }

    @Override
    public int addDefaultPrivilegesWhenAddRole(String roleid) {
        return mapper.addDefaultPrivilegesWhenAddRole(roleid);
    }

    @Override
    public int delPrivilegesWenDelRole(String roleid) {
        return mapper.delPrivilegesWenDelRole(roleid);
    }
}
