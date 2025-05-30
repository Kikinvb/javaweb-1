package cn.edu.jnu.baiscms.mapper;

import cn.edu.jnu.baiscms.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user(username,password,name,phone,email,address,avatar) values(#{username},#{password},#{name},#{phone},#{email},#{address},#{avatar})")
    public void insertUser(User user);

    @Update("update user set username=#{username}, password=#{password}, name=#{name},phone=#{phone},email=#{email},address=#{address},avatar=#{avatar} where id=#{id}")
    public void updateUser(User user);

    @Delete("delete from user where id=#{id}")
    public void deleteOne(Integer id);

    @Select("select * from user order by id desc")
    public List<User> selectAll();

    @Select("select * from user where id=#{id}")
    public User selectById(Integer id);

    @Select("select * from user where username=#{username} and name=#{name}")
    public List<User> selectByMore(String username, String name);

    @Select("select * from user where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%')")
    public List<User> selectLike(String username, String name);

    @Select("select * from user where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') limit #{skipNum}, #{pageSize}")
    public List<User> selectByPage(Integer skipNum, Integer pageSize, String username, String name);

    @Select("select count(id) from user where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%')")
    public Integer selectCount(String username, String name);

    @Select("select * from user where username=#{username}")
    public User selectByUsername(String username);
}