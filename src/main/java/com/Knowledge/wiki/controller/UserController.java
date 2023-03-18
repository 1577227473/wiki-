package com.Knowledge.wiki.controller;

import com.Knowledge.wiki.req.UserLoginReq;
import com.Knowledge.wiki.req.UserQueryReq;
import com.Knowledge.wiki.req.UserRestPasswordReq;
import com.Knowledge.wiki.req.UserSaveReq;
import com.Knowledge.wiki.resp.CommonResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.resp.UserLoginResp;
import com.Knowledge.wiki.resp.UserQueryResp;
import com.Knowledge.wiki.service.UserService;
import com.Knowledge.wiki.util.SnowFlake;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);


    @Resource
    private UserService userService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;


    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq userQueryReq) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(userQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq userReq) {
        userReq.setPassword(DigestUtils.md5DigestAsHex(userReq.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(userReq);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq userReq) {
        userReq.setPassword(DigestUtils.md5DigestAsHex(userReq.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp=userService.login(userReq);

        //生成单点登录token，并放入redis中
        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token:{}，并放入redis中",token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp),3600*24, TimeUnit.SECONDS);

        resp.setContent(userLoginResp);
        return resp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody UserRestPasswordReq Req) {
        Req.setPassword(DigestUtils.md5DigestAsHex(Req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.restPassword(Req);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token：{}",token);
        return resp;
    }

}
