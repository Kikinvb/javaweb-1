package cn.edu.jnu.baiscms.service;

import cn.edu.jnu.baiscms.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public void insertUser(User user);

    public void updateUser(User user);

    /**
     * 删除一条记录
     * @param id ID号
     */
    public void deleteOneUser(Integer id);

    /**
     * 删除多条记录
     * @param ids id列表
     */
    public void deleteManyUser(List<Integer> ids);

    public List<User> selectAll();

    /**
     * 根据ID查询单条记录
     * @param id
     * @return
     */
    public User selectById(Integer id);

    /**
     * 多条件查询
     * @param username
     * @param name
     * @return
     */
    public List<User> selectByMore(String username, String name);

    public List<User> selectLike(String username, String name);

    /**
     * 分页查询
     * @param pageNum 第几页
     * @param pageSize 每一页的记录数
     * @param username
     * @param name
     * @return
     */
    public Map<String, Object> selectByPage(Integer pageNum, Integer pageSize, String username, String name);

    public User login(User user);

    public User register(User user);
}
