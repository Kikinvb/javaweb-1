package cn.edu.jnu.baiscms.service.impl;

import cn.edu.jnu.baiscms.entity.User;
import cn.edu.jnu.baiscms.exception.ServiceException;
import cn.edu.jnu.baiscms.mapper.UserMapper;
import cn.edu.jnu.baiscms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteOneUser(Integer id) {
        userMapper.deleteOne(id);
    }

    @Override
    public void deleteManyUser(List<Integer> ids) {
        for (Integer id: ids) {
            userMapper.deleteOne(id);
        }
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username, name);
    }

    @Override
    public List<User> selectLike(String username, String name) {
        return userMapper.selectLike(username, name);
    }

    @Override
    public Map<String, Object> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
        Map<String, Object> map = new HashMap<>();

        int skipNum = (pageNum - 1) * pageSize;

        // 获得满足条件的记录
        List<User> users = userMapper.selectByPage(skipNum, pageSize, username, name);

        // 总条数
        Integer total = userMapper.selectCount(username, name);

        map.put("list", users);
        map.put("total", total);

        return map;
    }

    @Override
    public User login(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());

        // 用户名不存在
        if(dbUser==null){
            throw new ServiceException("用户名或密码错误");
        }

        // 密码不正确
        if(!dbUser.getPassword().equals(user.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }

        return dbUser;
    }

    @Override
    public User register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());

        if(dbUser!=null){
            throw new ServiceException("用户名已存在");
        }

        user.setName(user.getUsername());
        userMapper.insertUser(user);

        return user;
    }
}
