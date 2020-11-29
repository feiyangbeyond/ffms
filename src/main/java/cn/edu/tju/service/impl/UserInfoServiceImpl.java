package cn.edu.tju.service.impl;

import cn.edu.tju.mapper.UserInfoMapper;
import cn.edu.tju.entity.House;
import cn.edu.tju.entity.Role;
import cn.edu.tju.entity.UserInfo;
import cn.edu.tju.service.UserInfoService;
import cn.edu.tju.utils.PageModel;
import cn.edu.tju.utils.Result;
import cn.edu.tju.utils.ResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public int add(UserInfo userInfo) {
        // 密码加密
        userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
        // 添加用户
        int result = userInfoMapper.add(userInfo);
        if (userInfo.getRoleid() == 2) {
            // 如果是家庭管理员，则新增家庭
            House newHouse = new House();
            newHouse.setOwnerid(userInfo.getId());
            int r = userInfoMapper.addHouseId(newHouse);

            // 添加家庭成功后，绑定到该用户
            if (r == 1) {
                userInfo.setHouseid(newHouse.getId().toString());
                result = userInfoMapper.update(userInfo);
            }
        }
        return result;
    }

    @Override
    public int update(UserInfo userInfo) {
        return userInfoMapper.update(userInfo);
    }

    @Override
    public int delete(String id) {
        return userInfoMapper.delete(id);
    }

    @Override
    public UserInfo getUserInfo(UserInfo userInfo) {
        return userInfoMapper.getUserInfo(userInfo);
    }

    @Override
    public boolean userIsExisted(UserInfo userInfo) {
        UserInfo info = userInfoMapper.userIsExisted(userInfo);
        if (info == null) return false;
        return info.getPassword().equals(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
    }

    @Override
    public Result getUsersByWhere(PageModel<UserInfo> model) {
        try {
            List<UserInfo> users = userInfoMapper.getUsersByWhere(model);
            Result<UserInfo> result = ResultUtil.success(users);
            result.setTotal(userInfoMapper.getToatlByWhere(model));
            if (result.getTotal() == 0) {
                result.setMsg("没有查到相关数据");
            } else {
                result.setMsg("数据获取成功");
            }
            return result;
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @Override
    public List<Role> getAllRoles() {
        return userInfoMapper.getAllRoles();
    }

    @Override
    public int addRole(Role role) {
        return userInfoMapper.addRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return userInfoMapper.updateRole(role);
    }

    @Override
    public int deleteRole(String id) {
        return userInfoMapper.deleteRole(id);
    }

    @Override
    public Role getRoleById(String id) {
        return userInfoMapper.getRoleById(id);
    }

    @Override
    public int registry(UserInfo userInfo) {
        userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()));
        userInfo.setRoleid(3);
        userInfo.setHouseid(null);
        return userInfoMapper.add(userInfo);
    }
}
