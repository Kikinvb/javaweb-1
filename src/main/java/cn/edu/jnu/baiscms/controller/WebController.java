package cn.edu.jnu.baiscms.controller;

import cn.edu.jnu.baiscms.common.AuthAccess;
import cn.edu.jnu.baiscms.common.Result;
import cn.edu.jnu.baiscms.entity.User;
import cn.edu.jnu.baiscms.service.UserService;
import cn.edu.jnu.baiscms.utils.TokenUtils;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @Autowired
    UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result index(){
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        // 判断输入参数的合法性
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("输入数据不正确");
        }

        User dbUser = userService.login(user);

        // 生成token，传递给前端
        String token = TokenUtils.genToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);

        return Result.success(dbUser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        // 判断输入参数的合法性
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("输入数据不正确");
        }

        User dbUser = userService.register(user);

        return Result.success(dbUser);
    }
}
