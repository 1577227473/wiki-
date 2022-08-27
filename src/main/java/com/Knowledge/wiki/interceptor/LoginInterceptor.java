package com.Knowledge.wiki.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器：Spring框架特有的，常用于登录校验，权限校验，请求日志打印
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 打印请求信息
        LOG.info("------------- LoginInterceptor 开始 -------------");
        LOG.info("请求地址：{}{}",request.getRequestURL().toString(),request.getMethod());
        LOG.info("远程地址：{}",request.getRemoteAddr());

        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        return true;

//        // OPTIONS请求不做校验,
//        // 前后端分离的架构, 前端会发一个OPTIONS请求先做预检, 对预检请求不做校验
//        if(request.getMethod().toUpperCase().equals("OPTIONS")){
//            return true;
//        }
//
//        String path = request.getRequestURL().toString();
//        LOG.info("接口登录拦截：，path：{}", path);
//
//        //获取header的token参数
//        String token = request.getHeader("token");
//        LOG.info("登录校验开始，token：{}", token);
//        if (token == null || token.isEmpty()) {
//            LOG.info( "token为空，请求被拦截" );
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return false;
//        }
//        Object object = redisTemplate.opsForValue().get(token);
//        if (object == null) {
//            LOG.warn( "token无效，请求被拦截" );
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return false;
//        } else {
//            LOG.info("已登录：{}", object);
//            LoginUserContext.setUser(JSON.parseObject((String) object, UserLoginResp.class));
//            return true;
//        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("requestStartTime");
        LOG.info("------------- LoginInterceptor 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
////        LOG.info("LogInterceptor 结束");
//    }
}
