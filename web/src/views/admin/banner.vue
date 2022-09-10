<template>
  <a-carousel autoplay>
  <template #prevArrow>
    <div class="custom-slick-arrow" style="left: 10px; zindex: 1">
      <left-circle-outlined />
    </div>
  </template>
  <template #nextArrow>
    <div class="custom-slick-arrow" style="right: 10px">
      <right-circle-outlined />
    </div>
  </template>
    <div><img src="/image/banner1.png" alt="avatar" /></div>
    <div><img src="/image/banner2.png" alt="avatar" /></div>
    <div><img src="/image/banner3.png" alt="avatar" /></div>
    <div><img src="/image/banner4.png" alt="avatar" /></div>
    <div><img src="/image/banner5.png" alt="avatar" /></div>
  </a-carousel>
</template>

<script lang="ts">
import { LeftCircleOutlined, RightCircleOutlined } from '@ant-design/icons-vue';
import { defineComponent } from 'vue';
export default defineComponent({
  components: {
    LeftCircleOutlined,
    RightCircleOutlined,
  },
});
</script>

<style scoped>
/* For demo */
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 400px;
  line-height: 160px;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}
</style>
