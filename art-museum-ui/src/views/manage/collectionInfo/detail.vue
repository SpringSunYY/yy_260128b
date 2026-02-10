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
          <el-button :type="isCollect ? 'warning' : 'primary'"
                     :icon="isCollect ? 'el-icon-star-on' : 'el-icon-star-off'" @click="handleCollect">
            {{ isCollect ? '已收藏' : '收藏' }}
          </el-button>
        </div>
      </div>
      <div class="meta-tags" v-if="detail.status || detail.sortType || detail.categoryName">
        <dict-tag :options="dict.type.collection_status" :value="detail.status"/>
        <dict-tag :options="dict.type.collection_sort_type" :value="detail.sortType"/>
        <span class="category-badge" v-if="detail.categoryName">{{ detail.categoryName }}</span>
      </div>
    </div>

    <!-- 内容区域 -->
    <div class="content-section" v-if="!loading && detail.id">
      <!-- 基础信息 -->
      <div class="info-panel">
        <div class="info-row" v-if="detail.author">
          <span class="label">作者</span>
          <span class="value">{{ detail.author }}</span>
        </div>
        <div class="info-row" v-if="detail.era">
          <span class="label">年代</span>
          <span class="value">{{ detail.era }}</span>
        </div>
        <div class="info-row" v-if="detail.material">
          <span class="label">材质</span>
          <span class="value">{{ detail.material }}</span>
        </div>
        <div class="info-row" v-if="detail.size">
          <span class="label">尺寸</span>
          <span class="value">{{ detail.size }}</span>
        </div>
      </div>

    <!-- 商品列表 -->
    <div class="goods-section" v-if="goodsList.length > 0">
      <h3 class="section-heading">相关商品</h3>
      <div class="goods-grid">
        <div class="goods-card" v-for="goods in goodsList" :key="goods.id" @click="handleGoodsClick(goods)">
          <div class="goods-image">
            <image-preview :src="goods.imageSrc" fit="cover" />
          </div>
          <div class="goods-info">
            <h4 class="goods-name">{{ goods.name }}</h4>
            <p class="goods-price">¥{{ goods.price }}</p>
            <p class="goods-sales">销量 {{ goods.sales || 0 }} | 库存 {{ goods.inventory || 0 }}</p>
            <el-button type="primary" size="mini" class="detail-btn" @click.stop="handleGoodsClick(goods)">详情</el-button>
          </div>
        </div>
      </div>
      <div class="pagination-wrapper" id="goods-pagination" v-if="goodsTotal > goodsQueryParams.pageSize">
        <pagination
          v-show="goodsTotal > 0"
          :total="goodsTotal"
          :page.sync="goodsQueryParams.pageNum"
          :limit.sync="goodsQueryParams.pageSize"
          :auto-scroll="false"
          @pagination="getGoodsList"
        />
      </div>
    </div>

      <!-- 简介 -->
      <div class="text-panel" v-if="detail.introduction">
        <h3 class="section-heading">简介</h3>
        <div class="rich-content" v-html="detail.introduction"></div>
      </div>

      <!-- 详细解读 -->
      <div class="text-panel" v-if="detail.detailedInterpretation">
        <h3 class="section-heading">详细解读</h3>
        <div class="rich-content" v-html="detail.detailedInterpretation"></div>
      </div>

      <!-- 历史背景 -->
      <div class="text-panel" v-if="detail.historicalBackground">
        <h3 class="section-heading">历史背景</h3>
        <div class="rich-content" v-html="detail.historicalBackground"></div>
      </div>
    </div>

    <!-- 空状态 -->
    <div class="empty-state" v-else-if="!loading">
      <el-empty description="未找到藏品信息"></el-empty>
    </div>
  </div>
</template>

<script>
import {getCollectionInfoDetail} from "@/api/manage/collectionInfo";
import {listGoods} from "@/api/manage/goods";
import ImagePreview from "@/components/ImagePreview/index.vue";
import DictTag from "@/components/DictTag/index.vue";
import {addCollect} from "@/api/manage/collect";

export default {
  name: "CollectionInfoDetail",
  components: {ImagePreview, DictTag},
  dicts: ['collection_status', 'collection_sort_type'],
  data() {
    return {
      loading: true,
      detail: {},
      imageList: [],
      isCollect: false,
      id: null,
      // 商品列表相关
      goodsList: [],
      goodsTotal: 0,
      goodsQueryParams: {
        pageNum: 1,
        pageSize: 12,
        collectionId: null,
        status: '1'
      }
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
      this.id = id;
      this.loading = true;
      getCollectionInfoDetail(id).then(response => {
        this.detail = response.data || {};
        this.loading = false;

        if (this.detail.imageSrc) {
          this.imageList = this.detail.imageSrc.split(',').map(item => item.trim()).filter(item => item);
        }

        // 获取商品列表
        this.goodsQueryParams.collectionId = id;
        this.getGoodsList();

        this.isCollect = Math.random() > 0.5;
      }).catch(error => {
        console.error("获取详情失败", error);
        this.loading = false;
      });
    },
    getGoodsList() {
      listGoods(this.goodsQueryParams).then(response => {
        this.goodsList = response.rows || [];
        this.goodsTotal = response.total || 0;
      });
    },
    // 点击商品卡片
    handleGoodsClick(goods) {
      this.$router.push({
        name: 'GoodsDetail',
        query: { id: goods.id }
      });
    },
    handleCollect() {
      addCollect({targetId: this.id, type: '2'}).then(res => {
        this.isCollect = !this.isCollect;
        this.$modal.msgSuccess(this.isCollect ? "收藏成功" : "取消收藏成功");
      })
    }
  }
};
</script>

<style scoped lang="scss">
.detail-wrapper {
  margin-top: 2vh;
  min-height: 100vh;
  background-color: #fafafa;
  color: #333;
  padding-bottom: 60px;
}

// Hero 主图区域 - 顶部
.hero-section {
  width: 100%;
  height: 90vh;
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
    border-radius: 10px;
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

    .header-right {
      .el-button--warning {
        background-color: #ff9800;
        border-color: #ff9800;
        color: #fff;
      }
    }
  }

  .meta-tags {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
    margin-bottom: 20px;

    :deep(.el-tag) {
      background-color: #e3f2fd;
      border-color: #bbdefb;
      color: #1976d2;
      font-size: 12px;
      padding: 4px 12px;
      border-radius: 20px;
    }

    .category-badge {
      background-color: #f5f5f5;
      color: #666;
      font-size: 12px;
      padding: 4px 12px;
      border-radius: 20px;
    }
  }
}

// 内容区域
.content-section {
  max-width: 80%;
  margin: 0 auto;
  padding: 0 20px;
}

// 基础信息面板
.info-panel {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 30px;
  padding: 30px 0;
  border-bottom: 1px solid #eee;
  margin-bottom: 40px;

  .info-row {
    display: flex;
    flex-direction: column;
    gap: 8px;

    .label {
      font-size: 12px;
      color: #999;
      text-transform: uppercase;
      letter-spacing: 2px;
    }

    .value {
      font-size: 16px;
      color: #333;
      font-weight: 500;
    }
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

// 商品列表样式
.goods-section {
  max-width: 100%;
  margin: 0 auto;
  padding: 0 20px 40px;

  .section-heading {
    font-size: 20px;
    font-weight: 600;
    color: #1976d2;
    margin: 0 0 24px 0;
    padding-left: 16px;
    border-left: 4px solid #1976d2;
  }

    .goods-grid {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 20px;
    }

  .goods-card {
    background: #fff;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
    }

    .goods-image {
      width: 100%;
      height: 260px;
      overflow: hidden;

      :deep(.image-preview) {
        width: 100%;
        height: 100%;
      }
    }

    .goods-info {
      padding: 16px;
      position: relative;

      .goods-name {
        font-size: 14px;
        font-weight: 500;
        color: #333;
        margin: 0 0 8px 0;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .goods-price {
        font-size: 18px;
        font-weight: 600;
        color: #f5222d;
        margin: 0;
      }

      .goods-sales {
        font-size: 12px;
        color: #999;
        margin: 4px 0 8px 0;
      }

      .detail-btn {
        position: absolute;
        right: 16px;
        bottom: 16px;
      }
    }
  }

  .pagination-wrapper {
    display: flex;
    justify-content: center;
    margin-top: 30px;
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
    }
  }

  .content-section {
    max-width: 100%;
    padding: 0 16px;
  }

  .info-panel {
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
  }
}
</style>
