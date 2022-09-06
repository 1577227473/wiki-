package com.Knowledge.wiki.service;


import com.Knowledge.wiki.domain.User;
import com.Knowledge.wiki.domain.UserExample;
import com.Knowledge.wiki.exception.BusinessException;
import com.Knowledge.wiki.exception.BusinessExceptionCode;
import com.Knowledge.wiki.mapper.UserMapper;
import com.Knowledge.wiki.req.UserLoginReq;
import com.Knowledge.wiki.req.UserQueryReq;
import com.Knowledge.wiki.req.UserSaveReq;
import com.Knowledge.wiki.resp.UserLoginResp;
import com.Knowledge.wiki.resp.UserQueryResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.util.CopyUtil;
import com.Knowledge.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq userQueryReq)
    {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(!ObjectUtils.isEmpty(userQueryReq.getLoginName())){
            criteria.andLoginNameEqualTo(userQueryReq.getLoginName());
        }
        PageHelper.startPage(userQueryReq.getPage(), userQueryReq.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo=new PageInfo<>(userList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

//        List<UserResp> respList = new ArrayList<>();
//        for (User user : userList) {
//            //UserResp userResp = new UserResp();
//            //BeanUtils.copyProperties(user,userResp);
//            //对象复制
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            respList.add(userResp);
//        }

        //列表复制
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
    public void save(UserSaveReq req){
        User user=CopyUtil.copy(req,User.class);
        if(ObjectUtils.isEmpty(req.getId())){
            User userDB=selectByLoginName(req.getLoginName());
            if(ObjectUtils.isEmpty(userDB)){
                //Id为空，则新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                //用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            //Id不为空，则更新
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String LoginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 登录
     */
    public UserLoginResp login(UserLoginReq req){
        User userDb = selectByLoginName(req.getLoginName());
        if(ObjectUtils.isEmpty(userDb)){
            //用户名不存在
            LOG.info("用户名不存在,{}",req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
          if(userDb.getPassword().equals(req.getPassword())){
              //登录成功
              UserLoginResp userLoginResp = CopyUtil.copy(userDb, UserLoginResp.class);
              return  userLoginResp;
          } else {
              //密码不对
              LOG.info("密码不对,输入密码：{},数据库密码：{}",req.getLoginName(), userDb.getPassword());
              throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
          }
        }
    }
}
