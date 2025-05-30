package cn.edu.jnu.baiscms.utils;

import cn.edu.jnu.baiscms.entity.User;
import cn.edu.jnu.baiscms.mapper.UserMapper;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    private static UserMapper staticUserMapper;

    @Resource
    UserMapper userMapper;

    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }

    /**
     * 生成token
     * @param userId 用户名id
     * @param sign 密钥，常使用密码作为密钥
     * @return
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId)    // 将userId保存到token里面，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))   // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以password作为token的密钥
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");

            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(Integer.parseInt(userId));
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
