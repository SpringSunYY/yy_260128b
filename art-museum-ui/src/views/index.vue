<template>
  <div class="app-container">
    <div class="home-header">
      <h2 class="home-title">{{ title }}</h2>
    </div>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px" class="search-form">
      <el-form-item label="分类" prop="categoryId">
        <treeselect style="width: 200px" v-model="queryParams.categoryId" :options="categoryOptions" :normalizer="normalizer"
                    placeholder="请选择分类"/>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable @keyup.enter.native="handleSearch"/>
      </el-form-item>
      <el-form-item label="排序类型" prop="sortType">
        <el-select v-model="queryParams.sortType" placeholder="请选择排序类型" clearable>
          <el-option v-for="dict in dict.type.collection_sort_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="queryParams.author" placeholder="请输入作者" clearable @keyup.enter.native="handleSearch"/>
      </el-form-item>
      <el-form-item label="年代" prop="era">
        <el-input v-model="queryParams.era" placeholder="请输入年代" clearable @keyup.enter.native="handleSearch"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearch">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <div v-loading="loading" class="card-grid">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="item in collectionList" :key="item.id" class="mb20">
          <el-card :body-style="{ padding: '0px' }" shadow="hover" class="collection-card">
            <div class="card-image-container">
              <image-preview :src="item.imageSrc" :width="'100%'" :height="250"/>
            </div>
            <div class="card-content">
              <div class="card-header">
                <span class="card-title">{{ item.name }}</span>
                <div class="card-status">
                  <dict-tag :options="dict.type.collection_status" :value="item.status"/>
                </div>
              </div>
              <div class="card-meta">
                <div class="meta-item">
                  <i class="el-icon-time"></i>
                  <span>{{ item.era || '未知年代' }}</span>
                </div>
                <div class="meta-item">
                  <i class="el-icon-user"></i>
                  <span>{{ item.author || '未知作者' }}</span>
                </div>
              </div>
              <div class="card-footer">
                <div class="footer-left">
                  <span class="category-tag">{{ item.categoryName }}</span>
                </div>
                <div class="footer-right">
                  <dict-tag :options="dict.type.collection_sort_type" :value="item.sortType"/>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col v-if="collectionList.length === 0 && !loading" :span="24">
          <el-empty description="暂无藏品数据"></el-empty>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {listCollectionInfo} from "@/api/manage/collectionInfo";
import {listCategory} from "@/api/manage/category";
import ImagePreview from "@/components/ImagePreview/index.vue";
import DictTag from "@/components/DictTag/index.vue";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Home",
  components: {ImagePreview, DictTag, Treeselect},
  dicts: ['collection_status', 'collection_sort_type'],
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      loading: true,
      collectionList: [],
      // 分类标签树选项
      categoryOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        name: null,
        status: null,
        sortType: null,
        author: null,
        era: null
      },
      showSearch: true
    };
  },
  created() {
    this.getTreeselect();
    this.fetchData();
  },
  methods: {
    /** 查询分类标签下拉树结构 */
    getTreeselect() {
      listCategory().then(response => {
        this.categoryOptions = [];
        const data = {id: 0, name: '顶级节点', children: []};
        data.children = this.handleTree(response.data, "id", "parentId");
        this.categoryOptions.push(data);
      });
    },
    /** 转换分类标签数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children
      };
    },
    fetchData() {
      this.loading = true;
      listCollectionInfo(this.queryParams).then(response => {
        this.collectionList = response.rows || [];
        this.loading = false;
      }).catch(error => {
        console.error("获取藏品列表失败", error);
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleSearch() {
      this.queryParams.pageNum = 1;
      this.fetchData();
    },
    /** 重置按钮操作 */
    resetSearch() {
      this.queryParams.categoryId = null;
      this.queryParams.name = null;
      this.queryParams.status = null;
      this.queryParams.sortType = null;
      this.queryParams.author = null;
      this.queryParams.era = null;
      this.handleSearch();
    }
  }
};
</script>

<style scoped lang="scss">
.home-header {
  text-align: center;
  margin-bottom: 30px;
  padding: 20px 0;
  border-bottom: 1px solid #ebeef5;

  .home-title {
    font-size: 32px;
    color: #303133;
    font-weight: 600;
    letter-spacing: 2px;
    margin: 0;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  }
}

.search-form {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  border: 1px solid #ebeef5;
}

.card-grid {
  min-height: 400px;
}

.mb20 {
  margin-bottom: 20px;
}

.collection-card {
  border: none;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s ease;
  height: 100%;
  background: #fff;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  }

  :deep(.el-card__body) {
    height: 100%;
    display: flex;
    flex-direction: column;
  }
}

.card-image-container {
  width: 100%;
  height: 250px;
  overflow: hidden;
  background-color: #f5f7fa;

  img, .image-preview {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    transition: transform 0.3s ease;
  }

  &:hover {
    img, .image-preview {
      transform: scale(1.05);
    }
  }
}

.card-content {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #fff;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;

  .card-title {
    font-size: 18px;
    font-weight: 600;
    color: #303133;
    margin: 0;
    line-height: 1.4;
    flex: 1;
    margin-right: 10px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    /* stylelint-disable-next-line property-no-vendor-prefix */
    -webkit-box-orient: vertical;
    line-clamp: 2;
  }
}

.card-meta {
  margin-bottom: 15px;

  .meta-item {
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #909399;
    margin-bottom: 8px;

    i {
      margin-right: 6px;
      font-size: 14px;
    }
  }
}

.card-footer {
  margin-top: auto;
  padding-top: 15px;
  border-top: 1px solid #f2f6fc;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .category-tag {
    background-color: #f0f2f5;
    color: #606266;
    font-size: 12px;
    padding: 4px 10px;
    border-radius: 4px;
    display: inline-block;
  }

  .footer-right {
    :deep(.el-tag) {
      font-size: 12px;
    }
  }
}
</style>
