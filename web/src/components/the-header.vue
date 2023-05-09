<template>
  <a-layout-header class="header">
    <div style="float: left;padding-right: 45px">
      <span style="color: white;font-weight:bold">小西知识文库</span>
    </div>
    <div style="float: left;width: 80%">
      <a-menu
          theme="dark"
          mode="horizontal"
          :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="/">
          <router-link to="/">首页</router-link>
        </a-menu-item>
        <a-menu-item key="/admin/user" :style="user.id? {} :{display:'none'}">
          <router-link to="/admin/user">用户管理</router-link>
        </a-menu-item>
        <a-menu-item key="/admin/ebook" :style="user.id? {} :{display:'none'}">
          <router-link to="/admin/ebook">电子书管理</router-link>
        </a-menu-item>
        <a-menu-item key="/admin/category" :style="user.id? {} :{display:'none'}">
          <router-link to="/admin/category">分类管理</router-link>
        </a-menu-item>
        <a-menu-item key="/about" v-show="!user.id">
          <router-link to="/about">关于我们</router-link>
        </a-menu-item>

        <a class="login-menu" v-show="!user.id" @click="showLoginModal" >
          <span>登录</span>
        </a>
      </a-menu>
    </div>
    <div size="small" v-show="user.id" style="float: right;margin-right:30px;">
      <a-dropdown :placement="placement">
        <a-avatar size="large" src="/image/head.png" />
        <template #overlay>
          <a-menu @click="handleMenuClick">
            <a-menu-item key="1">
              <UserOutlined />
              我的
            </a-menu-item>
            <a-menu-item key="2" @click="logout">
              <PoweroffOutlined />
              退出登录
            </a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>

    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{span:6}" :wrapper-col="{ span: 18 }">
        <a-form-item label="登录名">
<!--          <a-input v-model:value="loginUser.loginName"/>-->
          <a-input v-model:value="loginUser.loginName" placeholder="请输入登录名">
            <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
          </a-input>
        </a-form-item>
        <a-form-item label="密码">
<!--          <a-input v-model:value="loginUser.password" type="password"/>-->
          <a-input v-model:value="loginUser.password" type="password" placeholder="请输入密码">
            <template #prefix><LockOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
          </a-input>
        </a-form-item>
      </a-form>
      <template #footer>
        <a-button key="back" @click="handleCancel">注册</a-button>
        <a-button key="submit" type="primary" :loading="loginModalLoading" @click="login">登录</a-button>
      </template>
    </a-modal>
  </a-layout-header>
</template>

<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";

declare let hexMd5:any;
declare let KEY:any;

export default defineComponent({
  name: 'the-header',
  setup(){
    //管理员验证登录
    const admin = ref();
    admin.value={};
    //登录
    const loginUser = ref();
    loginUser.value={};
    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () =>{
      loginModalVisible.value = true;
    };

    const user = computed(() => store.state.user);

    //登录
    const login = ()=>{
      console.log("开始登录");
      loginModalLoading.value = true;

      loginUser.value.password = hexMd5(loginUser.value.password + KEY);

      axios.post('/user/login',loginUser.value).then((response)=>{
        loginModalLoading.value = false;
        const data=response.data;
        if(data.success){
          loginModalVisible.value = false;
          message.success("登录成功！");
          store.commit("setUser", data.content);
          // if (user.value.id == 5556165418767871){
          //   admin.value = user.value;
          // }
        } else {
          message.error(data.message);
        }
      });
    };

    //退出登录
    const logout = ()=>{
      console.log("开始退出登录");
      axios.get('/user/logout/'+user.value.token).then((response)=>{
        const data=response.data;
        if(data.success){
          message.success("退出登录成功！");
          //清除store中的信息
          store.commit("setUser", {});
        } else {
          message.error(data.message);
        }
      });

    };

    return{
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      loginUser,
      login,
      user,
      logout,
      admin
    }
  }
});
</script>

<style>
  .login-menu {
    position:absolute;
    /*float: right;*/
    right:20px;
    color: white;
    /*padding-left: 10px;*/
  }
</style>
