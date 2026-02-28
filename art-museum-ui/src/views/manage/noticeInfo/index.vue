<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.notion_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_notice_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:noticeInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:noticeInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:noticeInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:noticeInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="noticeInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="标题" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="title"/>
      <el-table-column label="类型" align="center" v-if="columns[2].visible" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.notion_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="sortNum"/>
      <el-table-column label="状态" align="center" v-if="columns[4].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notice_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="关联藏品" align="center" v-if="columns[5].visible" prop="collectionIds">
        <template slot-scope="scope">
          <el-tag v-for="(tag, index) in scope.row.collectionNames ? scope.row.collectionNames.split(',') : []"
                  :key="`${scope.row.id}-${index}`"
                  :disable-transitions="false"
          >
            {{ tag }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="内容" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="content"/>
      <el-table-column label="收藏数" align="center" v-if="columns[7].visible" prop="collectNumber"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="remark"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="userName"/>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="updateBy"/>
      <el-table-column label="创建时间" align="center" v-if="columns[10].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" v-if="columns[11].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:noticeInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:noticeInfo:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['manage:noticeInfo:query']"
          >查看
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改资讯信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.notion_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入排序"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_notice_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="关联藏品" prop="collectionIds">
          <el-select
            v-model="form.collectionIds"
            filterable
            remote
            multiple
            reserve-keyword
            placeholder="请输入藏品名称"
            :remote-method="remoteGetCollectionInfoList"
            :loading="collectionInfoLoading">
            <el-option
              v-for="item in collectionInfoList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看详情对话框 -->
    <el-dialog title="咨询详情" :visible.sync="viewOpen" width="800px" append-to-body>
      <div class="notice-detail">
        <div class="detail-header">
          <h2 class="detail-title">{{ viewForm.title }}</h2>
          <div class="detail-tags">
            <dict-tag :options="dict.type.notion_type" :value="viewForm.type"/>
            <dict-tag :options="dict.type.sys_notice_status" :value="viewForm.status"/>
          </div>
        </div>

        <el-divider content-position="left">
          <span class="divider-title">基本信息</span>
        </el-divider>

        <div class="detail-content">
          <div class="info-grid">
            <div class="info-item">
              <span class="info-label">收藏数</span>
              <span class="info-value">{{ viewForm.collectNumber }}</span>
            </div>
            <div class="info-item" v-if="viewForm.userName">
              <span class="info-label">创建人</span>
              <span class="info-value">{{ viewForm.userName }}</span>
            </div>
            <div class="info-item" v-if="viewForm.createTime">
              <span class="info-label">创建时间</span>
              <span class="info-value">{{ parseTime(viewForm.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </div>
            <div class="info-item" v-if="viewForm.updateTime">
              <span class="info-label">更新时间</span>
              <span class="info-value">{{ parseTime(viewForm.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </div>
            <div class="info-item" v-if="viewForm.remark">
              <span class="info-label">备注</span>
              <span class="info-value">{{ viewForm.remark }}</span>
            </div>
          </div>
        </div>

        <el-divider content-position="left" v-if="viewForm.collectionNames">
          <span class="divider-title">关联藏品</span>
        </el-divider>

        <div class="collection-list" v-if="viewForm.collectionNames">
          <el-tag
            v-for="(name, index) in viewForm.collectionNames.split(',')"
            :key="`${index}-${viewForm.collectionIds.split(',')[index] || index}`"
            type="primary"
            size="medium"
            class="collection-tag"
            @click="handleViewCollection(viewForm.collectionIds.split(',')[index])">
            <i class="el-icon-picture-outline" style="margin-right: 4px;"></i>
            {{ name }}
          </el-tag>
        </div>

        <el-divider content-position="left" v-if="viewForm.content">
          <span class="divider-title">公告内容</span>
        </el-divider>

        <div class="content-box" v-html="viewForm.content"></div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button :type="viewForm.isCollect ? 'warning' : 'primary'"
                   :icon="viewForm.isCollect ? 'el-icon-star-on' : 'el-icon-star-off'" @click="handleCollect">
          {{ viewForm.isCollect ? '已收藏' : '收藏' }}
        </el-button>
        <el-button type="primary" plain @click="viewOpen = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addNoticeInfo,
  delNoticeInfo,
  getNoticeInfo,
  getNoticeInfoDetail,
  listNoticeInfo,
  updateNoticeInfo
} from "@/api/manage/noticeInfo";
import {listCollectionInfo} from "@/api/manage/collectionInfo";
import {addCollect} from "@/api/manage/collect";

export default {
  name: "NoticeInfo",
  dicts: ['after_sales_type', 'sys_notice_status', 'notion_type'],
  data() {
    return {
      collectionInfoList: [],
      collectionInfoLoading: false,
      collectionInfoQuery: {
        pageNum: 1,
        pageSize: 30,
        name: null,
        status: '1',
        createTime: null,
        updateTime: null,
      },
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: true},
        {key: 1, label: '标题', visible: true},
        {key: 2, label: '类型', visible: true},
        {key: 3, label: '排序', visible: false},
        {key: 4, label: '状态', visible: true},
        {key: 5, label: '关联藏品', visible: true},
        {key: 6, label: '内容', visible: false},
        {key: 7, label: '收藏数', visible: true},
        {key: 8, label: '备注', visible: false},
        {key: 9, label: '创建人', visible: true},
        {key: 10, label: '更新人', visible: false},
        {key: 11, label: '创建时间', visible: true},
        {key: 12, label: '更新时间', visible: false},
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 资讯信息表格数据
      noticeInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查看详情弹出层
      viewOpen: false,
      // 查看详情表单
      viewForm: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        title: null,
        type: null,
        status: null,
        collectionIds: null,
        updateBy: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/noticeInfo/export',
      // 表单校验
      rules: {
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "类型不能为空", trigger: "change"}
        ],
        sortNum: [
          {required: true, message: "排序不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        userId: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCollectionInfoList();
  },
  methods: {
    remoteGetCollectionInfoList(keyword) {
      this.collectionInfoQuery.name = keyword;
      this.getCollectionInfoList()
    },
    getCollectionInfoList() {
      this.collectionInfoLoading = true;
      listCollectionInfo(this.collectionInfoQuery).then(response => {
        this.collectionInfoList = response.rows;
        this.collectionInfoLoading = false;
      });
    },
    /** 查询资讯信息列表 */
    getList() {
      this.loading = true;
      listNoticeInfo(this.queryParams).then(response => {
        this.noticeInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        type: null,
        sortNum: null,
        status: null,
        collectionIds: [],
        content: null,
        remark: null,
        userId: null,
        updateBy: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加资讯信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getNoticeInfo(id).then(response => {
        this.form = response.data;
        this.form.collectionIds = this.form.collectionIds.split(",");
        this.open = true;
        this.title = "修改资讯信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.collectionIds = this.form.collectionIds.join(",");
          if (this.form.id != null) {
            updateNoticeInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNoticeInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除资讯信息编号为"' + ids + '"的数据项？').then(function () {
        return delNoticeInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(this.exportUrl, {
        ...this.queryParams
      }, `noticeInfo_${new Date().getTime()}.xlsx`)
    },
    /** 查看详情 */
    handleView(row) {
      getNoticeInfoDetail(row.id).then(response => {
        this.viewForm = response.data;
        this.viewOpen = true;
      })
    },
    /** 查看藏品详情 */
    handleViewCollection(id) {
      const route = this.$router.resolve({
        name: 'CollectionInfoDetail',
        query: {
          id: id
        }
      });
      window.open(route.href, '_blank');
    },
    handleCollect() {
      addCollect({targetId: this.viewForm.id, type: '1'}).then(res => {
        this.viewForm.isCollect = !this.viewForm.isCollect;
        this.$modal.msgSuccess(this.viewForm.isCollect ? "收藏成功" : "取消收藏成功");
      })
    }
  }
};
</script>

<style scoped>
.notice-detail {
  padding: 0 10px;
}

.detail-header {
  text-align: center;
  margin-bottom: 24px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ebeef5;
}

.detail-title {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 12px 0;
  line-height: 1.4;
}

.detail-tags {
  display: flex;
  justify-content: center;
  gap: 8px;
}

.divider-title {
  font-size: 14px;
  font-weight: 500;
  color: #909399;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.detail-content {
  margin-bottom: 20px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px 24px;
}

.info-item {
  display: flex;
  align-items: flex-start;
}

.info-label {
  font-size: 13px;
  color: #909399;
  white-space: nowrap;
  margin-right: 12px;
  min-width: 70px;
}

.info-value {
  font-size: 13px;
  color: #606266;
  word-break: break-all;
}

.collection-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  padding: 8px 0;
}

.collection-tag {
  cursor: pointer;
  transition: all 0.3s ease;
  border-style: dashed;
}

.collection-tag:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.content-box {
  font-size: 16px;
  line-height: 2;
  color: #555;
  word-break: break-word;
  overflow-wrap: break-word;
}

.content-box ::v-deep p {
  margin-bottom: 16px;
}

.content-box ::v-deep img {
  max-width: 100%;
  height: auto !important;
  object-fit: contain;
  border-radius: 8px;
  margin: 20px 0;
  display: block;
}

/* Element UI 样式覆盖 */
::v-deep .el-dialog__header {
  border-bottom: 1px solid #ebeef5;
  padding: 16px 20px 14px;
}

::v-deep .el-dialog__title {
  font-weight: 600;
  font-size: 16px;
  color: #303133;
}

::v-deep .el-divider--horizontal {
  margin: 20px 0;
}

::v-deep .el-divider__text {
  padding: 0 16px;
  background: transparent;
  font-size: 13px;
}
</style>
