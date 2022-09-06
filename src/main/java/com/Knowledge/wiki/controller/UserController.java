package com.Knowledge.wiki.controller;

import com.Knowledge.wiki.req.UserLoginReq;
import com.Knowledge.wiki.req.UserQueryReq;
import com.Knowledge.wiki.req.UserSaveReq;
import com.Knowledge.wiki.resp.CommonResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.resp.UserLoginResp;
import com.Knowledge.wiki.resp.UserQueryResp;
import com.Knowledge.wiki.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq userQueryReq) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(userQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq userReq) {
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
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp=userService.login(userReq);
        resp.setContent(userLoginResp);
        return resp;
    }

}