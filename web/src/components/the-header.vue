<template>
  <a-layout-header class="header">
    <div class="logo"></div>
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/">
        <router-link to="/">首页</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/user" :style="admin.id? {} :{display:'none'}">
        <router-link to="/admin/user">用户管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook" :style="admin.id? {} :{display:'none'}">
        <router-link to="/admin/ebook">电子书管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/category" :style="admin.id? {} :{display:'none'}">
        <router-link to="/admin/category">分类管理</router-link>
      </a-menu-item>

      <a-menu-item key="/about">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>

        <a-space size="small"  v-show="user.id" style="position:absolute;right:10px;">
          <a-button type="white"> 您好:{{user.name}}</a-button>
          <!--        <p>好:{{user.name}}</p>-->
          <a-popconfirm
              title="是否确认退出？"
              ok-text="是"
              cancel-text="否"
              @confirm="logout()"
          >
            <a-button  type="white" >退出登录</a-button>
          </a-popconfirm>
        </a-space>

      <a class="login-menu" v-show="!user.id" @click="showLoginModal" >
        <span>登录</span>
      </a>
    </a-menu>
    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{span:6}" :wrapper-col="{ span: 18 }">
        <a-form-item label="登录名">
          <a-input v-model:value="loginUser.loginName"/>
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="loginUser.password" type="password"/>
        </a-form-item>
      </a-form>
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
