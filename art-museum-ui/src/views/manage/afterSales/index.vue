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
      <el-form-item label="售后类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择售后类型" clearable>
          <el-option
            v-for="dict in dict.type.after_sales_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="审核人" prop="auditBy">
        <el-input
          v-model="queryParams.auditBy"
          placeholder="请输入审核人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['manage:afterSales:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:afterSales:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:afterSales:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:afterSales:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="afterSalesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id" />
        <el-table-column label="订单" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="orderId" />
        <el-table-column label="商品" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="goodsId" />
        <el-table-column label="用户" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="userId" />
        <el-table-column label="售后类型" align="center" v-if="columns[4].visible" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.after_sales_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
        <el-table-column label="申请理由" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="apply" />
        <el-table-column label="审核状态" align="center" v-if="columns[6].visible" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
        <el-table-column label="审核人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="auditBy" />
        <el-table-column label="审核时间" align="center" v-if="columns[8].visible" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="审核原因" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible" prop="auditDesc" />
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="remark" />
        <el-table-column label="创建时间" align="center" v-if="columns[11].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="updateBy" />
        <el-table-column label="更新时间" align="center" v-if="columns[13].visible" prop="updateTime" width="180">
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
            v-hasPermi="['manage:afterSales:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:afterSales:remove']"
          >删除</el-button>
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

    <!-- 添加或修改售后信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单" />
        </el-form-item>
        <el-form-item label="商品" prop="goodsId">
          <el-input v-model="form.goodsId" placeholder="请输入商品" />
        </el-form-item>
        <el-form-item label="用户" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户" />
        </el-form-item>
        <el-form-item label="售后类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择售后类型">
            <el-option
              v-for="dict in dict.type.after_sales_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请理由" prop="apply">
          <el-input v-model="form.apply" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-radio-group v-model="form.auditStatus">
            <el-radio
              v-for="dict in dict.type.audit_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核人" prop="auditBy">
          <el-input v-model="form.auditBy" placeholder="请输入审核人" />
        </el-form-item>
        <el-form-item label="审核时间" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择审核时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核原因" prop="auditDesc">
          <el-input v-model="form.auditDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listAfterSales, getAfterSales, delAfterSales, addAfterSales, updateAfterSales } from "@/api/manage/afterSales";

export default {
  name: "AfterSales",
  dicts: ['after_sales_type', 'audit_status'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: true },
          { key: 1, label: '订单', visible: true },
          { key: 2, label: '商品', visible: true },
          { key: 3, label: '用户', visible: true },
          { key: 4, label: '售后类型', visible: true },
          { key: 5, label: '申请理由', visible: true },
          { key: 6, label: '审核状态', visible: true },
          { key: 7, label: '审核人', visible: true },
          { key: 8, label: '审核时间', visible: true },
          { key: 9, label: '审核原因', visible: true },
          { key: 10, label: '备注', visible: true },
          { key: 11, label: '创建时间', visible: true },
          { key: 12, label: '更新人', visible: true },
          { key: 13, label: '更新时间', visible: true },
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
      // 售后信息表格数据
      afterSalesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeAuditTime: [],
      // 更新时间时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        type: null,
        auditStatus: null,
        auditBy: null,
        auditTime: null,
        createTime: null,
        updateBy: null,
      },
      // 表单参数
      form: {},
      // 导出地址
      exportUrl: 'manage/afterSales/export',
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单不能为空", trigger: "blur" }
        ],
        goodsId: [
          { required: true, message: "商品不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "用户不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "售后类型不能为空", trigger: "change" }
        ],
        apply: [
          { required: true, message: "申请理由不能为空", trigger: "blur" }
        ],
        auditStatus: [
          { required: true, message: "审核状态不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询售后信息列表 */
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
      listAfterSales(this.queryParams).then(response => {
        this.afterSalesList = response.rows;
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
        orderId: null,
        goodsId: null,
        userId: null,
        type: null,
        apply: null,
        auditStatus: null,
        auditBy: null,
        auditTime: null,
        auditDesc: null,
        remark: null,
        createTime: null,
        updateBy: null,
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
      this.daterangeAuditTime = [];
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加售后信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAfterSales(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改售后信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAfterSales(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAfterSales(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除售后信息编号为"' + ids + '"的数据项？').then(function() {
        return delAfterSales(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(this.exportUrl, {
        ...this.queryParams
      }, `afterSales_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
