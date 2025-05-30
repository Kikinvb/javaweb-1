package cn.edu.jnu.baiscms.common;

import cn.edu.jnu.baiscms.entity.User;
import cn.edu.jnu.baiscms.exception.ServiceException;
import cn.edu.jnu.baiscms.mapper.UserMapper;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Jwt的拦截器
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }

        // 判断处理方法上是否添加了AuthAccess注解，如果添加了则放行token验证
        if(handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }

        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }

        // 获取token中的user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e){
            throw new ServiceException("401", "请登录");
        }

        // 根据token中的userid查询数据库
        User user = userMapper.selectById(Integer.parseInt(userId));
        if (user == null) {
            throw new ServiceException("401", "请登录");
        }

        // 通过用户密码加密之后生成一个验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);  // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "请登录");
        }

        return true;
    }
}
