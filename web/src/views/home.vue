<template>
  <a-layout>
    <a-layout-sider width="200" theme="light">
      <a-menu
          mode="inline"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
          <MailOutlined />
          <span>欢迎</span>
        </a-menu-item>
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span><user-outlined />{{item.name}}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <MailOutlined /><span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout-content
        :style="{ padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <div class="welcome" v-show="isShowWelcome">
<!--        <h1>欢迎来到知识库系统</h1>-->
      <banner></banner>
        <br/>
      </div>
      <a-card title="热门文章" style="width: 100%">
        <template #extra><a href="#">更多</a></template>
        <a-list item-layout="horizontal" :data-source="data">
          <template #renderItem="{ item }">
            <a-list-item>
              <a-list-item-meta
                  description="Ant Design, a design language for background applications, is refined by Ant UED Team"
              >
                <template #title>
                  <a href="https://www.antdv.com/">{{ item.title }}</a>
                </template>
                <template #avatar>
                  <a-avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-card>


    </a-layout-content>

  </a-layout>
</template>

<script lang="ts">
import { StarOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue';
/*import { defineComponent } from 'vue';*/

import { defineComponent,onMounted,ref,reactive,toRef } from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import Banner from "@/views/admin/banner.vue";
import TheFooter from "@/components/the-footer.vue";


export default defineComponent({
  components: {
    TheFooter,
    Banner,
    StarOutlined,
    LikeOutlined,
    MessageOutlined,
  },
  name: 'Home',
  setup(){
    const ebooks=ref();

    const level1 =  ref();
    let categorys: any;
    /**
     * 查询所有分类
     **/
    const handleQueryCategory = () => {
      axios.get("/category/all").then((response) => {
        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("原始数组：", categorys);
          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("树形结构：", level1.value);
        } else {
          message.error(data.message);
        }
      });
    };

    const isShowWelcome = ref(true);
    let categoryId2=0;

    const handleQueryEbook = () =>{
      axios.get("/ebook/list",{
        params:{
          page:1,
          size:1000,
          categoryId2: categoryId2
        }
      }).then(function(response){
        const data=response.data;
        ebooks.value=data.content.list;
      });
    };

    const handleClick = (value:any) => {
      if(value.key === 'welcome'){
        isShowWelcome.value = true;
      } else {
        categoryId2 = value.key;
        isShowWelcome.value=false;
        handleQueryEbook();
      }
    };

    const onCollapse = (collapsed: boolean, type: string) => {
      console.log(collapsed, type);
    };

    const onBreakpoint = (broken: boolean) => {
      console.log(broken);
    };

    const data = [
      {
        title: 'Ant Design Title 1',
      },
      {
        title: 'Ant Design Title 2',
      },
      {
        title: 'Ant Design Title 3',
      },
      {
        title: 'Ant Design Title 4',
      },
    ];

    onMounted(function (){
      handleQueryCategory();
    });

    return{
      ebooks,
      pagination : {
        onChange: (page: any) => {
          console.log(page);
        },
        pageSize: 3,
      },
      actions:[
      { type: 'StarOutlined', text: '156' },
      { type: 'LikeOutlined', text: '156' },
      { type: 'MessageOutlined', text: '2' },
     ],

      onBreakpoint,
      onCollapse,

      handleClick,
      level1,

      data,
      isShowWelcome
    }
  }
});
</script>

<style scoped>
  .site-layout .site-layout-background {
    background: #fff;
  }
  #root,body,html {
    height: 100%;
  }

  .ant-layout {
    display: flex!important;
    width: 100%!important;
    min-height: 100%!important;
  }
</style>
