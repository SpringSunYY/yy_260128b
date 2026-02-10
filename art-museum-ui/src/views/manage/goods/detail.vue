<template>
  <div class="detail-wrapper" v-loading="loading">
    <!-- 主图展示区 - 顶部 -->
    <div class="hero-section" v-if="imageList.length > 0">
      <div class="hero-carousel">
        <el-carousel :interval="6000" arrow="always" height="80vh" trigger="click">
          <el-carousel-item v-for="(img, index) in imageList" :key="index">
            <div class="hero-image-wrapper">
              <image-preview :src="img" fit="contain"/>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>
    <div class="hero-section empty" v-else>
      <div class="no-image">
        <i class="el-icon-picture-outline"></i>
        <span>暂无图片</span>
      </div>
    </div>

    <!-- 标题区域 -->
    <div class="header-bar">
      <div class="header-content">
        <h1 class="item-title">{{ detail.name }}</h1>
        <div class="header-right">
          <span class="price-tag">¥{{ detail.price }}</span>
        </div>
      </div>
      <div class="meta-info">
        <span v-if="detail.sales">销量 {{ detail.sales }}</span>
        <span v-if="detail.inventory">库存 {{ detail.inventory }}</span>
        <span class="status-badge" :class="{ 'on-sale': detail.status === '1' }">
          {{ detail.status === '1' ? '在售' : '下架' }}
        </span>
      </div>
    </div>

    <!-- 内容区域 -->
    <div class="content-section" v-if="!loading && detail.id">
      <!-- 关联藏品 -->
      <div class="related-collection" v-if="collectionInfo" @click="goCollectionDetail">
        <div class="collection-thumb">
          <image-preview :src="collectionInfo.imageSrc" fit="cover" />
        </div>
        <div class="collection-info">
          <h4>相关藏品</h4>
          <p>{{ collectionInfo.name }}</p>
        </div>
        <i class="el-icon-arrow-right"></i>
      </div>

      <!-- 商品描述 -->
      <div class="text-panel" v-if="detail.description">
        <h3 class="section-heading">商品描述</h3>
        <div class="rich-content" v-html="detail.description"></div>
      </div>
    </div>

    <!-- 空状态 -->
    <div class="empty-state" v-else-if="!loading">
      <el-empty description="未找到商品信息"></el-empty>
    </div>
  </div>
</template>

<script>
import {getGoods} from "@/api/manage/goods";
import {getCollectionInfoDetail} from "@/api/manage/collectionInfo";
import ImagePreview from "@/components/ImagePreview/index.vue";

export default {
  name: "GoodsDetail",
  components: {ImagePreview},
  data() {
    return {
      loading: true,
      detail: {},
      collectionInfo: null,
      imageList: []
    };
  },
  created() {
    this.getDetail();
  },
  methods: {
    getDetail() {
      const id = this.$route.query.id;
      if (!id) {
        this.$message.error('参数缺失');
        this.loading = false;
        return;
      }
      this.loading = true;
      getGoods(id).then(response => {
        this.detail = response.data || {};
        this.loading = false;

        if (this.detail.imageSrc) {
          this.imageList = this.detail.imageSrc.split(',').map(item => item.trim()).filter(item => item);
        }

        // 获取关联藏品信息
        if (this.detail.collectionId) {
          this.getCollectionInfo(this.detail.collectionId);
        }
      }).catch(error => {
        console.error("获取详情失败", error);
        this.loading = false;
      });
    },
    getCollectionInfo(id) {
      getCollectionInfoDetail(id).then(response => {
        this.collectionInfo = response.data || {};
      });
    },
    goCollectionDetail() {
      if (this.collectionInfo && this.collectionInfo.id) {
        this.$router.push({
          name: 'CollectionInfoDetail',
          query: { id: this.collectionInfo.id }
        });
      }
    }
  }
};
</script>

<style scoped lang="scss">
.detail-wrapper {
  min-height: 100vh;
  background-color: #fafafa;
  color: #333;
  padding-bottom: 60px;
}

// Hero 主图区域 - 顶部
.hero-section {
  width: 100%;
  height: 80vh;
  max-height: 80vh;
  background: #fff;
  border-bottom: 1px solid #eee;

  &.empty {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .no-image {
    display: flex;
    flex-direction: column;
    align-items: center;
    color: #999;

    i {
      font-size: 64px;
      margin-bottom: 16px;
    }
  }

  .hero-carousel {
    width: 100%;
    height: 100%;
  }

  .hero-image-wrapper {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 30px;

    :deep(.image-preview) {
      max-width: 100%;
      max-height: 100%;
    }
  }

  :deep(.el-carousel) {
    height: 100%;

    .el-carousel__container {
      height: 100%;
    }

    .el-carousel__item {
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .el-carousel__arrow {
      background-color: rgba(0, 0, 0, 0.3);
      color: #fff;
      font-size: 24px;

      &:hover {
        background-color: rgba(0, 0, 0, 0.5);
      }
    }

    .el-carousel__indicators--outside {
      margin-top: 20px;

      .el-carousel__indicator {
        padding: 8px;

        .el-carousel__button {
          width: 8px;
          height: 8px;
          border-radius: 50%;
          background-color: #ccc;
          opacity: 1;
        }

        &.is-active .el-carousel__button {
          background-color: #409eff;
        }
      }
    }
  }
}

// 标题区域
.header-bar {
  max-width: 75%;
  margin: 40px auto 0;
  padding: 0 20px;

  .header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;

    .item-title {
      font-size: 32px;
      font-weight: 600;
      color: #1a1a1a;
      margin: 0;
      line-height: 1.4;
    }

    .price-tag {
      font-size: 32px;
      font-weight: 600;
      color: #f5222d;
    }
  }

  .meta-info {
    display: flex;
    gap: 20px;
    align-items: center;
    margin-bottom: 20px;
    font-size: 14px;
    color: #666;

    .status-badge {
      padding: 4px 12px;
      border-radius: 20px;
      background-color: #f5f5f5;
      color: #999;

      &.on-sale {
        background-color: #f6ffed;
        color: #52c41a;
      }
    }
  }
}

// 内容区域
.content-section {
  max-width: 80%;
  margin: 0 auto;
  padding: 0 20px;
}

// 关联藏品
.related-collection {
  display: flex;
  align-items: center;
  padding: 20px;
  background: #fff;
  border-radius: 12px;
  margin-bottom: 30px;
  cursor: pointer;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
  }

  .collection-thumb {
    width: 80px;
    height: 80px;
    border-radius: 8px;
    overflow: hidden;
    margin-right: 16px;

    :deep(.image-preview) {
      width: 100%;
      height: 100%;
    }
  }

  .collection-info {
    flex: 1;

    h4 {
      font-size: 12px;
      color: #999;
      margin: 0 0 4px 0;
      text-transform: uppercase;
      letter-spacing: 2px;
    }

    p {
      font-size: 16px;
      font-weight: 500;
      color: #333;
      margin: 0;
    }
  }

  .el-icon-arrow-right {
    font-size: 20px;
    color: #999;
  }
}

// 文本面板
.text-panel {
  margin-bottom: 40px;

  .section-heading {
    font-size: 20px;
    font-weight: 600;
    color: #1976d2;
    margin: 0 0 20px 0;
    padding-left: 16px;
    border-left: 4px solid #1976d2;
  }

  .rich-content {
    font-size: 16px;
    line-height: 2;
    color: #555;

    ::v-deep p {
      margin-bottom: 16px;
    }

    ::v-deep img {
      max-width: 100%;
      height: auto;
      border-radius: 8px;
      margin: 20px 0;
    }
  }
}

// 空状态
.empty-state {
  min-height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

// 响应式
@media (max-width: 768px) {
  .hero-section {
    height: 60vh;
    max-height: 60vh;
  }

  .header-bar {
    margin-top: 24px;

    .header-content {
      flex-direction: column;
      align-items: flex-start;
      gap: 12px;

      .item-title {
        font-size: 24px;
      }

      .price-tag {
        font-size: 28px;
      }
    }
  }

  .content-section {
    max-width: 100%;
    padding: 0 16px;
  }

  .related-collection {
    .collection-thumb {
      width: 60px;
      height: 60px;
    }
  }
}
</style>
