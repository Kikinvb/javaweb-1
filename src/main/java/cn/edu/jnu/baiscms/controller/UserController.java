package cn.edu.jnu.baiscms.controller;

import cn.edu.jnu.baiscms.common.Result;
import cn.edu.jnu.baiscms.entity.User;
import cn.edu.jnu.baiscms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        try {
            userService.insertUser(user);
        }catch (Exception e) {
            if (e instanceof DuplicateKeyException){
                return Result.error("数据库插入错误");
            }else{
                return Result.error("系统错误");
            }
        }

        return Result.success();
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){
        userService.updateUser(user);

        return Result.success();
    }

    @DeleteMapping("/deleteOne/{id}")
    public Result deleteOne(@PathVariable Integer id){
        userService.deleteOneUser(id);

        return Result.success();
    }

    @DeleteMapping("/deleteMany")
    public Result deleteMany(@RequestBody List<Integer> ids){
        userService.deleteManyUser(ids);

        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> users = userService.selectAll();

        return Result.success(users);
    }

    @GetMapping("/selectbyid/{id}")
    public Result selectById(@PathVariable Integer id){
        User user = userService.selectById(id);

        return Result.success(user);
    }

    @GetMapping("/selectbymore")
    public Result selectByMore(@RequestParam String username, @RequestParam String name){
        List<User> users = userService.selectByMore(username, name);

        return Result.success(users);
    }

    @GetMapping("/selectlike")
    public Result selectLike(@RequestParam String username, @RequestParam String name){
        List<User> users = userService.selectLike(username,name);

        return Result.success(users);
    }

    @GetMapping("/selectpage")
    public Result selectPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username, @RequestParam String name){
        Map<String, Object> map = userService.selectByPage(pageNum, pageSize, username, name);

        return Result.success(map);
    }
}
