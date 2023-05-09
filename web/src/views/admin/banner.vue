<template>
  <div style="width: 78%;margin-left: 10%">
    <a-carousel autoplay arrows>
      <template #prevArrow>
        <div class="custom-slick-arrow" style="left: 10px;">
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
  </div>
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
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 400px;
  line-height: 160px;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  opacity: 0.3;
}
.ant-carousel :deep(.custom-slick-arrow:before) {
  display: none;
}
.ant-carousel :deep(.custom-slick-arrow:hover) {
  opacity: 0.5;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}

</style>
