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
        <div class="info-row" v-if="detail.galleryName">
          <span class="label">美术馆</span>
          <span class="value">{{ detail.galleryName }}</span>
        </div>
        <div class="info-row" v-if="detail.categoryName">
          <span class="label">分类</span>
          <span class="value">{{ detail.categoryName }}</span>
        </div>
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

      <!-- 多媒体列表 -->
      <div class="multimedia-section" v-if="multimediaList && multimediaList.length > 0">
        <h3 class="section-heading">相关多媒体</h3>
        <div class="multimedia-grid">
          <div class="multimedia-card" v-for="(item, index) in multimediaList" :key="index">
            <div class="multimedia-preview">
              <!-- 图片类型：显示预览图 -->
              <template v-if="isImageFile(item.fileUrl)">
                <image-preview :src="getFirstFile(item.fileUrl)" fit="contain"/>
              </template>
              <!-- 音频类型：显示播放按钮 -->
              <template v-else-if="isAudioFile(item.fileUrl)">
                <div class="audio-placeholder" @click="playAudio(item)">
                  <i class="el-icon-headset"></i>
                  <span>音频</span>
                </div>
              </template>
              <!-- 视频类型：显示播放按钮 -->
              <template v-else-if="isVideoFile(item.fileUrl)">
                <div class="video-placeholder" @click="playVideo(item)">
                  <i class="el-icon-video-camera"></i>
                  <span>视频</span>
                </div>
              </template>
              <!-- 其他类型：显示文件图标 -->
              <template v-else>
                <div class="file-placeholder">
                  <i class="el-icon-document"></i>
                  <span>文件</span>
                </div>
              </template>
            </div>
            <div class="multimedia-info">
              <h4 class="multimedia-name">{{ item.name }}</h4>
              <div class="multimedia-tags">
                <dict-tag :options="dict.type.collection_multimedia_type" :value="item.type"/>
                <el-button
                  type="text"
                  size="mini"
                  icon="el-icon-download"
                  @click.stop="downloadFile(item)"
                  class="download-btn"
                >下载
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 音频播放器组件 -->
      <GlobalAudioPlayer ref="audioPlayer"/>

      <!-- 商品列表 -->
      <div class="goods-section" v-if="goodsList.length > 0">
        <h3 class="section-heading">相关商品</h3>
        <div class="goods-grid">
          <div class="goods-card" v-for="goods in goodsList" :key="goods.id">
            <div class="goods-image">
              <image-preview :src="goods.imageSrc" fit="cover"/>
            </div>
            <div class="goods-info">
              <h4 class="goods-name">{{ goods.name }}</h4>
              <p class="goods-price">¥{{ goods.price }}</p>
              <p class="goods-sales">销量 {{ goods.sales || 0 }} | 库存 {{ goods.inventory || 0 }}</p>
              <el-button type="primary" size="mini" class="detail-btn" @click.stop="handleGoodsClick(goods)">详情
              </el-button>
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

      <!-- 评价信息 -->
      <div class="evaluate-section">
        <h3 class="section-heading">评价信息</h3>

        <!-- 评价列表表格 -->
        <el-table :data="evaluateList" v-loading="evaluateLoading" style="width: 100%">
          <el-table-column label="标题" :show-overflow-tooltip="true" align="center" prop="title" />
          <el-table-column label="评分" align="center" prop="score" width="120">
            <template slot-scope="scope">
              <el-rate v-model="scope.row.score" :max="5" :allow-half="false" disabled show-score text-color="#ff9900" />
            </template>
          </el-table-column>
          <el-table-column label="评价内容" :show-overflow-tooltip="true" align="center" prop="content" />
          <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" prop="userName" width="120" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination-wrapper" v-if="evaluateTotal > evaluateQueryParams.pageSize">
          <pagination
            v-show="evaluateTotal > 0"
            :total="evaluateTotal"
            :page.sync="evaluateQueryParams.pageNum"
            :limit.sync="evaluateQueryParams.pageSize"
            @pagination="getEvaluateList"
          />
        </div>

        <!-- 添加评价按钮 -->
        <div class="add-evaluate-btn">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddEvaluate">添加评价</el-button>
        </div>
      </div>

      <!-- 添加评价对话框 -->
      <el-dialog title="添加评价" :visible.sync="evaluateOpen" width="500px" append-to-body>
        <el-form ref="evaluateForm" :model="evaluateForm" :rules="evaluateRules" label-width="80px">
          <el-form-item label="标题" prop="title">
            <el-input v-model="evaluateForm.title" placeholder="请输入标题" />
          </el-form-item>
          <el-form-item label="评分" prop="score">
            <el-rate v-model="evaluateForm.score" :max="5" :allow-half="false" show-score text-color="#ff9900" />
          </el-form-item>
          <el-form-item label="评价内容" prop="content">
            <el-input type="textarea" v-model="evaluateForm.content" placeholder="请输入评价内容" :rows="4" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitEvaluateForm">确 定</el-button>
          <el-button @click="cancelEvaluateForm">取 消</el-button>
        </div>
      </el-dialog>
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
import {listEvaluate, addEvaluate, listEvaluateByCollection} from "@/api/manage/evaluate";
import ImagePreview from "@/components/ImagePreview/index.vue";
import DictTag from "@/components/DictTag/index.vue";
import GlobalAudioPlayer from "@/components/GlobalAudioPlayer/index.vue";
import audioBus from "@/utils/audioBus";
import {addCollect} from "@/api/manage/collect";

export default {
  name: "CollectionInfoDetail",
  components: {ImagePreview, DictTag, GlobalAudioPlayer},
  dicts: ['collection_status', 'collection_sort_type', 'collection_multimedia_type', 'evaluate_status'],
  data() {
    return {
      loading: true,
      detail: {},
      imageList: [],
      isCollect: false,
      id: null,
      // 多媒体列表
      multimediaList: [],
      // 商品列表相关
      goodsList: [],
      goodsTotal: 0,
      goodsQueryParams: {
        pageNum: 1,
        pageSize: 12,
        collectionId: null,
        status: '1'
      },
      // 评价列表相关
      evaluateList: [],
      evaluateLoading: false,
      collectTotal: 0,
      evaluateQueryParams: {
        pageNum: 1,
        pageSize: 10,
        collectionId: null,
        status: '0'
      },
      // 添加评价对话框
      evaluateOpen: false,
      evaluateForm: {
        title: '',
        score: 5,
        content: '',
        status: '0'
      },
      evaluateRules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        score: [
          { required: true, message: "评分不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "评价内容不能为空", trigger: "blur" }
        ]
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

        // 获取多媒体列表
        this.multimediaList = response.data.multimediaList || [];

        // 获取商品列表
        this.goodsQueryParams.collectionId = id;
        this.getGoodsList();

        // 获取评价列表
        this.evaluateQueryParams.collectionId = id;
        this.getEvaluateList();

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
    // 获取评价列表
    getEvaluateList() {
      this.evaluateLoading = true;
      listEvaluateByCollection(this.evaluateQueryParams).then(response => {
        this.evaluateList = response.rows || [];
        this.evaluateTotal = response.total || 0;
        this.evaluateLoading = false;
      }).catch(error => {
        console.error("获取评价列表失败", error);
        this.evaluateLoading = false;
      });
    },
    // 添加评价按钮
    handleAddEvaluate() {
      this.evaluateForm = {
        title: '',
        score: 5,
        content: '',
        status: '0',
        collectionId: this.id
      };
      this.evaluateOpen = true;
    },
    // 取消评价对话框
    cancelEvaluateForm() {
      this.evaluateOpen = false;
      this.resetEvaluateForm();
    },
    // 重置评价表单
    resetEvaluateForm() {
      this.evaluateForm = {
        title: '',
        score: 5,
        content: '',
        status: '0'
      };
      this.resetForm("evaluateForm");
    },
    // 提交评价表单
    submitEvaluateForm() {
      this.$refs["evaluateForm"].validate(valid => {
        if (valid) {
          addEvaluate(this.evaluateForm).then(response => {
            this.$modal.msgSuccess("添加评价成功");
            this.evaluateOpen = false;
            this.getEvaluateList();
          }).catch(error => {
            console.error("添加评价失败", error);
          });
        }
      });
    },
    // 点击商品卡片（打开新窗口）
    handleGoodsClick(goods) {
      const routeData = this.$router.resolve({
        name: 'GoodsDetail',
        query: {id: goods.id}
      });
      window.open(routeData.href, '_blank');
    },

    // 获取文件名
    getFileName(filePath) {
      if (!filePath) return '';
      const arr = filePath.split('/');
      return arr[arr.length - 1];
    },

    // 获取文件路径（完整访问地址）
    getFilePath(filePath) {
      if (!filePath) return '';
      // 如果是完整URL则直接返回，否则拼接基础路径
      if (filePath.startsWith('http://') || filePath.startsWith('https://')) {
        return filePath;
      }
      return process.env.VUE_APP_BASE_API + filePath;
    },

    // 判断是否为图片文件
    isImageFile(fileUrl) {
      if (!fileUrl) return false;
      const ext = this.getFileExtension(fileUrl).toLowerCase();
      return ['png', 'jpg', 'jpeg'].includes(ext);
    },

    // 判断是否为音频文件
    isAudioFile(fileUrl) {
      if (!fileUrl) return false;
      const ext = this.getFileExtension(fileUrl).toLowerCase();
      return ['mp3'].includes(ext);
    },

    // 判断是否为视频文件
    isVideoFile(fileUrl) {
      if (!fileUrl) return false;
      const ext = this.getFileExtension(fileUrl).toLowerCase();
      return ['mp4'].includes(ext);
    },

    // 获取文件扩展名
    getFileExtension(fileUrl) {
      if (!fileUrl) return '';
      const arr = fileUrl.split('.');
      if (arr.length > 1) {
        return arr[arr.length - 1];
      }
      return '';
    },

    // 获取第一个文件路径
    getFirstFile(fileUrl) {
      if (!fileUrl) return '';
      const files = fileUrl.split(',');
      return files[0].trim();
    },


    // 播放音频（使用 GlobalAudioPlayer 组件）
    playAudio(item) {
      audioBus.$emit("play", item);
      // 更新播放列表
      audioBus.$emit("updatePlaylist", this.multimediaList.filter(m => this.isAudioFile(m.fileUrl)));
    },

    // 下载文件
    downloadFile(item) {
      const files = item.fileUrl.split(',');
      // 如果只有一个文件，直接下载
      if (files.length === 1) {
        const fileUrl = this.getFilePath(files[0].trim());
        const fileName = this.getFileName(files[0].trim());
        this.downloadByUrl(fileUrl, fileName);
      } else {
        // 多个文件时依次下载
        files.forEach(file => {
          const fileUrl = this.getFilePath(file.trim());
          const fileName = this.getFileName(file.trim());
          this.downloadByUrl(fileUrl, fileName);
        });
      }
    },

    // 通过URL下载文件
    downloadByUrl(url, fileName) {
      const link = document.createElement('a');
      link.href = url;
      link.download = fileName;
      link.target = '_blank';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },

    // 播放视频（打开新窗口）
    playVideo(item) {
      const videoUrl = this.getFirstFile(item.fileUrl);
      const fullUrl = this.getFilePath(videoUrl);
      window.open(
        this.$router.resolve({
          name: 'CollectionMultimediaPlay',
          query: {url: encodeURIComponent(fullUrl), name: item.name}
        }).href,
        '_blank'
      );
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
  max-width: 80%;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
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

// 多媒体列表样式
.multimedia-section {
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

  .multimedia-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
  }

  .multimedia-card {
    background: #fff;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    border: 1px solid #eee;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
    }

    .multimedia-preview {
      position: relative;
      width: 100%;
      height: 160px;
      overflow: hidden;
      background: #f5f5f5;

      .image-only {
        width: 100%;
        height: 100%;

        :deep(.image-preview) {
          width: 100%;
          height: 100%;
        }
      }

      .audio-placeholder,
      .video-placeholder {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        transition: all 0.3s ease;

        i {
          font-size: 40px;
          margin-bottom: 8px;
        }

        span {
          font-size: 13px;
          opacity: 0.9;
        }
      }

      .audio-placeholder {
        background: linear-gradient(135deg, #e8f5e9 0%, #c8e6c9 100%);
        color: #2e7d32;

        &:hover {
          background: linear-gradient(135deg, #c8e6c9 0%, #a5d6a7 100%);
        }
      }

      .video-placeholder {
        background: linear-gradient(135deg, #fff3e0 0%, #ffe0b2 100%);
        color: #e65100;

        &:hover {
          background: linear-gradient(135deg, #ffe0b2 0%, #ffcc80 100%);
        }
      }

      .file-placeholder {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background: linear-gradient(135deg, #eceff1 0%, #cfd8dc 100%);
        color: #546e7a;

        i {
          font-size: 40px;
          margin-bottom: 8px;
        }

        span {
          font-size: 13px;
        }
      }
    }

    .multimedia-info {
      padding: 12px;

      .multimedia-name {
        font-size: 14px;
        font-weight: 500;
        color: #333;
        margin: 0 0 8px 0;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .multimedia-tags {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .el-tag {
          flex-shrink: 0;
        }

        .download-btn {
          padding: 0;
          font-size: 12px;
          color: #1976d2;

          &:hover {
            color: #1565c0;
          }
        }
      }
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

// 评价信息样式
.evaluate-section {
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

  .add-evaluate-btn {
    margin-top: 20px;
    text-align: center;
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

  .multimedia-section {
    max-width: 100%;
    padding: 0 16px 30px;

    .multimedia-grid {
      grid-template-columns: repeat(2, 1fr);
      gap: 16px;
    }

    .multimedia-card {
      .multimedia-preview {
        height: 140px;

        .audio-placeholder,
        .video-placeholder,
        .file-placeholder {
          i {
            font-size: 36px;
          }
        }
      }
    }
  }
}
</style>
