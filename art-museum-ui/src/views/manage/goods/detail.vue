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
        <el-button
          v-if="detail.status === '1'"
          type="primary"
          size="large"
          icon="el-icon-shopping-cart-2"
          @click="openBuyDialog"
          class="buy-btn">
          立即购买
        </el-button>
      </div>
    </div>

    <!-- 内容区域 -->
    <div class="content-section" v-if="!loading && detail.id">
      <!-- 关联藏品 -->
      <div class="related-collection" v-if="collectionInfo" @click="goCollectionDetail">
        <div class="collection-thumb">
          <image-preview :src="collectionInfo.imageSrc" fit="cover"/>
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

    <!-- 购买对话框 -->
    <el-dialog title="购买商品" :visible.sync="buyDialogVisible" width="600px" append-to-body>
      <el-form :model="buyForm" label-width="80px">
        <el-form-item label="商品名称">
          <span>{{ detail.name }}</span>
        </el-form-item>
        <el-form-item label="商品价格">
          <span style="color: #f5222d; font-size: 18px; font-weight: bold;">¥{{ detail.price }}</span>
        </el-form-item>
        <el-form-item label="选择地址" prop="addressId">
          <el-select
            v-model="buyForm.addressId"
            placeholder="请选择收货地址"
            style="width: 100%;"
            filterable
            remote
            reserve-keyword
            :remote-method="remoteGetAddress"
            :loading="addressLoading">
            <el-option
              v-for="item in addressList"
              :key="item.id"
              :label="`${item.phone} | ${item.province}${item.city}${item.county}${item.address}`"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="购买数量" prop="numbers">
          <el-input-number v-model="buyForm.numbers" :min="1" :max="detail.inventory || 999"
                           controls-position="right"></el-input-number>
          <span class="inventory-hint" v-if="detail.inventory">（库存：{{ detail.inventory }}件）</span>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="buyForm.remark" type="textarea" placeholder="请输入备注信息" rows="3"></el-input>
        </el-form-item>
        <el-form-item label="订单总价">
          <span style="color: #f5222d; font-size: 20px; font-weight: bold;">¥{{
              (detail.price * buyForm.numbers).toFixed(2)
            }}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="buyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitBuy">确认购买</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getGoods} from "@/api/manage/goods";
import {getCollectionInfoDetail} from "@/api/manage/collectionInfo";
import {addOrder} from "@/api/manage/order";
import {listUserAddress} from "@/api/manage/userAddress";
import ImagePreview from "@/components/ImagePreview/index.vue";

export default {
  name: "GoodsDetail",
  components: {ImagePreview},
  data() {
    return {
      loading: true,
      detail: {},
      collectionInfo: null,
      imageList: [],
      // 购买对话框
      buyDialogVisible: false,
      buyForm: {
        addressId: null,
        numbers: 1,
        remark: ''
      },
      // 地址选择相关
      addressList: [],
      addressLoading: false,
      addressQuery: {
        pageNum: 1,
        pageSize: 100
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
          query: {id: this.collectionInfo.id}
        });
      }
    },
    // 打开购买对话框
    openBuyDialog() {
      this.buyForm = {
        addressId: null,
        numbers: 1,
        remark: ''
      };
      this.loadAddressList();
      this.buyDialogVisible = true;
    },
    // 加载地址列表
    loadAddressList() {
      this.addressLoading = true;
      listUserAddress(this.addressQuery).then(response => {
        this.addressList = response.rows || [];
        this.addressLoading = false;
      }).catch(() => {
        this.addressLoading = false;
      });
    },
    remoteGetAddress(keyword) {
      this.addressQuery.province = keyword;
      this.loadAddressList()
    },
    // 提交购买
    submitBuy() {
      // if (!this.buyForm.addressId) {
      //   this.$message.warning('请选择收货地址');
      //   return;
      // }
      if (this.buyForm.numbers < 1) {
        this.$message.warning('购买数量必须大于0');
        return;
      }
      if (this.detail.inventory && this.buyForm.numbers > this.detail.inventory) {
        this.$message.warning(`库存不足，最大可购买${this.detail.inventory}件`);
        return;
      }

      const orderData = {
        goodsId: this.detail.id,
        numbers: this.buyForm.numbers,
        addressId: this.buyForm.addressId,
        remark: this.buyForm.remark,
        totalPrice: this.detail.price * this.buyForm.numbers
      };

      addOrder(orderData).then(response => {
        this.$modal.msgSuccess('订单创建成功，请前往付款！');
        this.buyDialogVisible = false;
        // 可选：跳转到订单列表页
        // this.$router.push({ name: 'Order' });
      }).catch(error => {
        console.error("创建订单失败", error);
      });
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
    flex-wrap: wrap;

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

// 购买按钮
.buy-btn {
  margin-left: 20px;
  background-color: #1976d2;
  border-color: #1976d2;
  padding: 10px 30px;
  font-size: 16px;
  border-radius: 4px;

  &:hover {
    background-color: #1565c0;
    border-color: #1565c0;
  }
}

.inventory-hint {
  margin-left: 10px;
  font-size: 12px;
  color: #999;
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
