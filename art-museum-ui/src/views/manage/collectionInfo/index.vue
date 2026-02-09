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
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.collection_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序类型" prop="sortType">
        <el-select v-model="queryParams.sortType" placeholder="请选择排序类型" clearable>
          <el-option
            v-for="dict in dict.type.collection_sort_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年代" prop="era">
        <el-input
          v-model="queryParams.era"
          placeholder="请输入年代"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['manage:collectionInfo:add']"
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
          v-hasPermi="['manage:collectionInfo:edit']"
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
          v-hasPermi="['manage:collectionInfo:remove']"
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
          v-hasPermi="['manage:collectionInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="collectionInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="name"/>
      <el-table-column label="相关图片" align="center" v-if="columns[2].visible" prop="imageSrc" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imageSrc" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" v-if="columns[3].visible" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.collection_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="排序类型" align="center" v-if="columns[4].visible" prop="sortType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.collection_sort_type" :value="scope.row.sortType"/>
        </template>
      </el-table-column>
      <el-table-column label="作者" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="author"/>
      <el-table-column label="年代" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="era"/>
      <el-table-column label="材质" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="material"/>
      <el-table-column label="尺寸" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="size"/>
      <el-table-column label="简介" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="introduction"/>
      <el-table-column label="详细解读" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="detailedInterpretation"/>
      <el-table-column label="历史背景" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="historicalBackground"/>
      <el-table-column label="收藏数" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="collectNumber"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible"
                       prop="remark"/>
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="userName"/>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible"
                       prop="updateBy"/>
      <el-table-column label="创建时间" align="center" v-if="columns[16].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" v-if="columns[17].visible" prop="updateTime" width="180">
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
            v-hasPermi="['manage:collectionInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:collectionInfo:remove']"
          >删除
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

    <!-- 添加或修改藏品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="作者" prop="author">
              <el-input v-model="form.author" placeholder="请输入作者"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年代" prop="era">
              <el-input v-model="form.era" placeholder="请输入年代"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="材质" prop="material">
              <el-input v-model="form.material" placeholder="请输入材质"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="尺寸" prop="size">
              <el-input v-model="form.size" placeholder="请输入尺寸"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序类型" prop="sortType">
              <el-select v-model="form.sortType" placeholder="请选择排序类型">
                <el-option
                  v-for="dict in dict.type.collection_sort_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.collection_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="相关图片" prop="imageSrc">
              <image-upload v-model="form.imageSrc"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="简介">
              <editor v-model="form.introduction" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详细解读">
              <editor v-model="form.detailedInterpretation" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="历史背景">
              <editor v-model="form.historicalBackground" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addCollectionInfo,
  delCollectionInfo,
  getCollectionInfo,
  listCollectionInfo,
  updateCollectionInfo
} from "@/api/manage/collectionInfo";

export default {
  name: "CollectionInfo",
  dicts: ['collection_sort_type', 'collection_status'],
  data() {
    return {
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: true},
        {key: 1, label: '名称', visible: true},
        {key: 2, label: '相关图片', visible: true},
        {key: 3, label: '状态', visible: true},
        {key: 4, label: '排序类型', visible: true},
        {key: 5, label: '作者', visible: true},
        {key: 6, label: '年代', visible: true},
        {key: 7, label: '材质', visible: true},
        {key: 8, label: '尺寸', visible: true},
        {key: 9, label: '简介', visible: false},
        {key: 10, label: '详细解读', visible: false},
        {key: 11, label: '历史背景', visible: false},
        {key: 12, label: '收藏数', visible: true},
        {key: 13, label: '备注', visible: false},
        {key: 14, label: '创建人', visible: true},
        {key: 15, label: '更新人', visible: false},
        {key: 16, label: '创建时间', visible: true},
        {key: 17, label: '更新时间', visible: false},
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
      // 藏品信息表格数据
      collectionInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        name: null,
        status: null,
        sortType: null,
        author: null,
        era: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/collectionInfo/export',
      // 表单校验
      rules: {
        name: [
          {required: true, message: "名称不能为空", trigger: "blur"}
        ],
        imageSrc: [
          {required: true, message: "相关图片不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        sortType: [
          {required: true, message: "排序类型不能为空", trigger: "change"}
        ],
        collectNumber: [
          {required: true, message: "收藏数不能为空", trigger: "blur"}
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
  },
  methods: {
    /** 查询藏品信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listCollectionInfo(this.queryParams).then(response => {
        this.collectionInfoList = response.rows;
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
        name: null,
        imageSrc: null,
        status: null,
        sortType: null,
        author: null,
        era: null,
        material: null,
        size: null,
        introduction: null,
        detailedInterpretation: null,
        historicalBackground: null,
        collectNumber: null,
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
      this.daterangeCreateTime = [];
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
      this.title = "添加藏品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCollectionInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改藏品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCollectionInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCollectionInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除藏品信息编号为"' + ids + '"的数据项？').then(function () {
        return delCollectionInfo(ids);
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
      }, `collectionInfo_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
