<template>
  <div class="video-play-page" v-loading="loading" element-loading-background="rgba(0, 0, 0, 0.8)">
    <div class="video-header">
      <el-button icon="el-icon-back" class="close-btn" @click="goBack">返回</el-button>
      <div class="video-title" v-if="videoName">{{ videoName }}</div>
      <div class="codec-tip">若有声无画，请检查视频是否为 H.264 编码，或者下载源文件</div>
    </div>

    <div class="video-container">
      <div id="dplayer" ref="dplayer"></div>
    </div>

    <div class="empty-state" v-if="errorMsg">
      <i class="el-icon-video-pause" style="font-size: 48px; margin-bottom: 10px;"></i>
      <h3>播放失败</h3>
      <p>{{ errorMsg }}</p>
      <el-button type="primary" size="small" @click="initPlayer" style="margin-top: 15px;">重试</el-button>
    </div>
  </div>
</template>

<script>
import DPlayer from 'dplayer';

export default {
  name: 'VideoPlayPage',
  data() {
    return {
      loading: true,
      videoUrl: '',
      videoName: '',
      dp: null,
      errorMsg: ''
    };
  },
  mounted() {
    this.initData();
  },
  beforeDestroy() {
    // 必须销毁实例，释放大视频占用的几百兆内存
    if (this.dp) {
      this.dp.destroy();
    }
  },
  methods: {
    initData() {
      const url = this.$route.query.url;
      const name = this.$route.query.name;

      if (url) {
        this.videoUrl = decodeURIComponent(url);
        this.videoName = name || '视频预览';
        this.initPlayer();
      } else {
        this.errorMsg = '未获取到视频地址';
        this.loading = false;
      }
    },

    initPlayer() {
      this.loading = true;
      this.errorMsg = '';

      this.$nextTick(() => {
        try {
          this.dp = new DPlayer({
            container: this.$refs.dplayer,
            autoplay: false, // 大视频建议设为 false，由程序控制播放
            screenshot: true,
            hotkey: true,
            preload: 'metadata', // 关键：只预取元数据，节省 500MB 视频的初始带宽
            theme: '#409EFF',
            video: {
              url: this.videoUrl,
              type: 'auto'
            }
          });

          // 视频元数据加载完成
          this.dp.on('loadedmetadata', () => {
            console.log('视频元数据已加载');
            this.loading = false;
          });

          // 监听播放异常
          this.dp.on('error', () => {
            this.loading = false;
            this.errorMsg = '视频解码失败或网络超时，请确保视频为 H.264 格式';
          });

          // 尝试自动播放逻辑
          this.attemptPlay();

        } catch (err) {
          this.errorMsg = '播放器初始化失败';
          this.loading = false;
        }
      });
    },

    attemptPlay() {
      // 浏览器通常禁止带声音的自动播放，这里做个优雅降级
      const playPromise = this.dp.play();
      if (playPromise !== undefined) {
        playPromise.catch(() => {
          console.warn("自动播放被拦截，已静音尝试");
          this.dp.notice('点击播放按钮开始观看', 3000);
        });
      }
    },

    goBack() {
      if (window.history.length <= 1) {
        window.close();
      } else {
        this.$router.go(-1);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.video-play-page {
  width: 100%;
  height: 100vh;
  background: #000;
  display: flex;
  flex-direction: column;
}

.video-header {
  height: 50px;
  background: rgba(40, 40, 40, 0.9);
  display: flex;
  align-items: center;
  padding: 0 20px;
  z-index: 10;
  border-bottom: 1px solid #444;

  .close-btn {
    background: #444;
    border: none;
    color: #fff;

    &:hover {
      background: #666;
    }
  }

  .video-title {
    margin-left: 20px;
    color: #eee;
    font-size: 14px;
    flex: 1;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .codec-tip {
    font-size: 12px;
    color: #e6a23c;
    margin-left: 10px;
  }
}

.video-container {
  flex: 1;
  background: #000;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;

  #dplayer {
    width: 100%;
    height: 100%;
  }
}

.empty-state {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  color: #ccc;
  z-index: 5;

  p {
    font-size: 14px;
    color: #999;
  }
}

/* 覆盖 DPlayer 全屏时的层级问题 */
:deep(.dplayer-video-wrap) {
  background: #000;
}
</style>
