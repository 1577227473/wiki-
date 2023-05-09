<template>
  <a-layout>
    <a-affix :offset-top="top">
      <the-header></the-header>
    </a-affix>
    <router-view></router-view>
    <the-footer style="height: 40px"></the-footer>
  </a-layout>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import TheHeader from "@/components/the-header.vue";
  import TheFooter from "@/components/the-footer.vue";
  export default defineComponent({
    name: 'app',
    components:{
      TheHeader,
      TheFooter,
    }
});
</script>

<style>
html, body, #app {
  height: 100%;
}

#root,body,html {
  height: 100%;
}
.ant-layout {
  display: flex!important;
  width: 100%!important;
  min-height: 100%!important;
}
.ant-layout-footer {
  padding: 0px 0px;
  font-size: 14px;
}
</style>
