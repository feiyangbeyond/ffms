package cn.edu.tju.mapper;

import cn.edu.tju.entity.House;
import cn.edu.tju.entity.Role;
import cn.edu.tju.entity.UserInfo;
import cn.edu.tju.utils.PageModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper {

    /**
     * 获取单个用户信息，可用于：
     * 1.登录
     * 2.通过用户某一部分信息获取用户完整信息
     *
     * @param userInfo
     * @return
     */
    UserInfo getUserInfo(UserInfo userInfo);

    /**
     * 通过username判断该用户是否存在
     *
     * @param userInfo
     * @return
     */
    UserInfo userIsExisted(UserInfo userInfo);

    /**
     * 通过条件获取符合条件的优化信息 -- 分页
     *
     * @param model
     * @return
     */
    List<UserInfo> getUsersByWhere(PageModel<UserInfo> model);

    int getToatlByWhere(PageModel<UserInfo> model);

    int add(UserInfo userInfo);

    int update(UserInfo userInfo);

    int delete(String id);

    List<Role> getAllRoles();

    int addRole(Role role);

    int updateRole(Role role);

    int deleteRole(String id);

    Role getRoleById(String id);

    int addHouseId(House house);
}
