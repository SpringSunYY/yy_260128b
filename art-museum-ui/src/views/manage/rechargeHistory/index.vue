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
      <el-form-item label="用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable>
          <el-option
            v-for="dict in dict.type.audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="审核人" prop="auditBy">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.auditBy"-->
      <!--          placeholder="请输入审核人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="审核时间">
        <el-date-picker
          v-model="daterangeAuditTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['manage:rechargeHistory:add']"
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
          v-hasPermi="['manage:rechargeHistory:edit']"
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
          v-hasPermi="['manage:rechargeHistory:remove']"
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
          v-hasPermi="['manage:rechargeHistory:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rechargeHistoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="用户" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="userName"/>
      <el-table-column label="充值价格" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="rechargePrice"/>
      <el-table-column label="充值凭证" align="center" v-if="columns[3].visible" prop="rechargeVoucher" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.rechargeVoucher" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" v-if="columns[4].visible" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审核人" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="auditBy"/>
      <el-table-column label="审核时间" align="center" v-if="columns[6].visible" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核原因" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="auditDesc"/>
      <el-table-column label="创建时间" align="center" v-if="columns[8].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="updateBy"/>
      <el-table-column label="更新时间" align="center" v-if="columns[10].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:rechargeHistory:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:rechargeHistory:remove']"
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

    <!-- 添加或修改充值记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="用户" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入用户" />-->
        <!--        </el-form-item>-->
        <el-form-item label="充值价格" prop="rechargePrice">
          <el-input v-model="form.rechargePrice" placeholder="请输入充值价格"/>
        </el-form-item>
        <el-form-item label="充值凭证" prop="rechargeVoucher">
          <image-upload v-model="form.rechargeVoucher"/>
        </el-form-item>
        <!--        <el-form-item label="审核状态" prop="auditStatus">-->
        <!--          <el-radio-group v-model="form.auditStatus">-->
        <!--            <el-radio-->
        <!--              v-for="dict in dict.type.audit_status"-->
        <!--              :key="dict.value"-->
        <!--              :label="parseInt(dict.value)"-->
        <!--            >{{dict.label}}</el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="审核人" prop="auditBy">-->
        <!--          <el-input v-model="form.auditBy" placeholder="请输入审核人" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="审核时间" prop="auditTime">-->
        <!--          <el-date-picker clearable-->
        <!--            v-model="form.auditTime"-->
        <!--            type="date"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            placeholder="请选择审核时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="审核原因" prop="auditDesc">-->
        <!--          <el-input v-model="form.auditDesc" type="textarea" placeholder="请输入内容" />-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
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
  addRechargeHistory,
  delRechargeHistory,
  getRechargeHistory,
  listRechargeHistory,
  updateRechargeHistory
} from "@/api/manage/rechargeHistory";

export default {
  name: "RechargeHistory",
  dicts: ['audit_status'],
  data() {
    return {
      //表格展示列
      columns: [
        {key: 0, label: '编号', visible: true},
        {key: 1, label: '用户', visible: true},
        {key: 2, label: '充值价格', visible: true},
        {key: 3, label: '充值凭证', visible: true},
        {key: 4, label: '审核状态', visible: true},
        {key: 5, label: '审核人', visible: true},
        {key: 6, label: '审核时间', visible: true},
        {key: 7, label: '审核原因', visible: true},
        {key: 8, label: '创建时间', visible: true},
        {key: 9, label: '更新人', visible: false},
        {key: 10, label: '更新时间', visible: false},
        {key: 11, label: '备注', visible: false},
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
      // 充值记录表格数据
      rechargeHistoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeAuditTime: [],
      // 备注时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        userId: null,
        auditStatus: null,
        auditBy: null,
        auditTime: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/rechargeHistory/export',
      // 表单校验
      rules: {
        userId: [
          {required: true, message: "用户不能为空", trigger: "blur"}
        ],
        rechargePrice: [
          {required: true, message: "充值价格不能为空", trigger: "blur"}
        ],
        rechargeVoucher: [
          {required: true, message: "充值凭证不能为空", trigger: "blur"}
        ],
        auditStatus: [
          {required: true, message: "审核状态不能为空", trigger: "change"}
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
    /** 查询充值记录列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeAuditTime && '' != this.daterangeAuditTime) {
        this.queryParams.params["beginAuditTime"] = this.daterangeAuditTime[0];
        this.queryParams.params["endAuditTime"] = this.daterangeAuditTime[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listRechargeHistory(this.queryParams).then(response => {
        this.rechargeHistoryList = response.rows;
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
        userId: null,
        rechargePrice: null,
        rechargeVoucher: null,
        auditStatus: null,
        auditBy: null,
        auditTime: null,
        auditDesc: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.daterangeAuditTime = [];
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
      this.title = "添加充值记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRechargeHistory(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改充值记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRechargeHistory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRechargeHistory(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除充值记录编号为"' + ids + '"的数据项？').then(function () {
        return delRechargeHistory(ids);
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
      }, `rechargeHistory_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
