<template>
  <div class="app-container home">
    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <template #header>
        <div class="card-header">
          <i class="el-icon-search"></i>
          <span>时间查询</span>
        </div>
      </template>
      <div class="search-form">
        <el-form :inline="true" :model="query" class="demo-form-inline">
          <el-form-item label="时间">
            <el-date-picker
              v-model="dataRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>


    <!-- 图表展示区域 -->
    <div class="charts-section">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <i class="el-icon-pie-chart"></i>
                <span>{{ collectionRankStatisticsName }}</span>
              </div>
            </template>
            <div class="chart-container">
              <BarRankingZoomCharts
                direction="right"
                :chart-data="collectionRankStatisticsData"
                :chart-title="collectionRankStatisticsName"
              />
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <i class="el-icon-pie-chart"></i>
                <span>{{ collectionStatisticsName }}</span>
              </div>
            </template>
            <div class="chart-container">
              <BarLineZoomCharts
                :chart-data="collectionStatisticsData"
                :chart-title="collectionStatisticsName"
              />
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <i class="el-icon-pie-chart"></i>
                <span>{{ noticeStatisticsName }}</span>
              </div>
            </template>
            <div class="chart-container">
              <BarAutoCarouselCharts
                :chart-data="noticeStatisticsData"
                :chart-title="noticeStatisticsName"
              />
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <template #header>
              <div class="card-header">
                <i class="el-icon-pie-chart"></i>
                <span>{{ noticeRankStatisticsName }}</span>
              </div>
            </template>
            <div class="chart-container">
              <BarRankingZoomCharts
                :chart-data="noticeRankStatisticsData"
                :chart-title="noticeRankStatisticsName"
              />
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import PieRoseCharts from "@/components/Echarts/PieRoseCharts.vue";
import PieRoseHollowCharts from "@/components/Echarts/PieRoseHollowCharts.vue";

import BarLineZoomCharts from "@/components/Echarts/BarLineZoomCharts.vue";
import dayjs from "dayjs";
import BarAutoCarouselCharts from "@/components/Echarts/BarAutoCarouselCharts.vue";
import BarRankingZoomCharts from "@/components/Echarts/BarRankingZoomCharts.vue";
import {collectRankStatistics, collectStatistics} from "@/api/manage/statistics";

const defaultStart = dayjs().subtract(14, "day").format("YYYY-MM-DD")
const defaultEnd = dayjs().format("YYYY-MM-DD")
export default {
  name: "Statistics",
  components: {BarRankingZoomCharts, BarAutoCarouselCharts, BarLineZoomCharts, PieRoseHollowCharts, PieRoseCharts},
  data() {
    return {
      currentDate: dayjs().format("YYYY-MM-DD HH:mm:ss"),
      // 查询参数
      query: {
        startTime: defaultStart,
        endTime: defaultEnd
      },
      collectTotal: 0,
      //藏品收藏
      collectionRankStatisticsData: [],
      collectionRankStatisticsName: "藏品排行",
      //讲座
      collectionStatisticsData: [],
      collectionStatisticsName: "藏品统计",
      collectionTotal: 0,
      //讲座排行
      noticeRankStatisticsData: [],
      noticeRankStatisticsName: "咨询排行",
      //预约
      noticeStatisticsData: [],
      noticeStatisticsName: "咨询分析",
      noticeTotal: 0,
      dataRange: [defaultStart, defaultEnd],
    }
  },
  created() {
    this.getStatistics()
  },
  methods: {
    //藏品收藏
    getCollectionStatisticsData() {
      collectStatistics({
        ...this.query,
        type: '2'
      }).then(res => {
        this.collectionStatisticsData = res.data
      })
    },
    //藏品排行
    getCollectionRankStatisticsData() {
      collectRankStatistics({
        ...this.query,
        type: '2'
      }).then(res => {
        this.collectionRankStatisticsData = res.data
      })
    },
    //咨询收藏
    getNoticeStatisticsData() {
      collectStatistics({
        ...this.query,
        type: '1'
      }).then(res => {
        this.noticeStatisticsData = res.data
      })
    },
    //咨询排行
    getNoticeRankStatisticsData() {
      collectRankStatistics({
        ...this.query,
        type: '1'
      }).then(res => {
        this.noticeRankStatisticsData = res.data
      })
    },
    getStatistics() {
      this.getCollectionStatisticsData()
      this.getNoticeStatisticsData()
      this.getCollectionRankStatisticsData()
      this.getNoticeRankStatisticsData()
    },

    handleQuery() {
      if (this.dataRange.length > 0) {
        this.query.startTime = this.dataRange[0]
        this.query.endTime = this.dataRange[1]
      }
      this.getStatistics()
    },
    resetQuery() {
      this.query = {}
      this.handleQuery();
    },
  }
}

</script>

<style scoped lang="scss">
.home {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: calc(100vh - 84px);
}

// 搜索区域样式
.search-card {
  margin-bottom: 20px;

  .card-header {
    display: flex;
    align-items: center;
    font-weight: 600;
    color: #409eff;

    i {
      margin-right: 8px;
      font-size: 16px;
    }
  }

  .search-form {
    .el-form-item {
      margin-bottom: 0;

      &:last-child .el-form-item__content {
        display: flex;
        gap: 10px;
      }
    }
  }
}


// 图表展示区域
.charts-section {
  background-color: #f8f9fa;
  border-radius: 8px;

  .chart-card {
    height: 100%;
    margin-top: 1vh;

    .card-header {
      display: flex;
      align-items: center;
      font-weight: 600;
      color: #409eff;
      border-bottom: 1px solid #ebeef5;
      padding-bottom: 12px;

      i {
        margin-right: 8px;
        font-size: 16px;
      }
    }

    .chart-container {
      height: 400px;
      display: flex;
      align-items: center;
      justify-content: center;

      :deep(.echarts-container) {
        width: 100% !important;
        height: 100% !important;
      }
    }
  }

  .el-row {
    margin-bottom: 0;
  }
}

// 无权限页面样式
.no-permission-section {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;

  .simple-card {
    max-width: 400px;
    text-align: center;

    .simple-content {
      h2 {
        color: #303133;
        font-size: 24px;
        font-weight: 600;
        margin: 0;
      }
    }
  }
}

// 响应式设计
@media screen and (max-width: 768px) {
  .home {
    padding: 10px;
  }

  .charts-section {
    padding: 10px;

    .el-col {
      margin-bottom: 20px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }

  .no-permission-section {
    .simple-card {
      .simple-content {
        h2 {
          font-size: 20px;
        }
      }
    }
  }
}
</style>

