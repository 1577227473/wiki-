<template>
  <a-layout-footer style="background-color: #e9e7ef;height: 100%">
    <div style="float: top;margin-left: 47%;margin-top: 10px">
      <span>小西知识文库</span>
    </div>
  </a-layout-footer>
</template>

<script lang="ts">
import store from '@/store';
import { defineComponent,computed } from 'vue';

export default defineComponent({
  name: 'the-footer',
  setup(){
    const user = computed(() => store.state.user);

    return{
      user
    }
  }
});


</script>

<style>
.ant-layout-footer {
  padding: 0px 0px!important;
  font-size: 14px!important;
}
</style>
