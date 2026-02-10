<template>
  <div class="video-player-wrapper">
    <div class="video-player-container">
      <video
        ref="videoPlayer"
        class="video-player"
        controls
        :poster="poster"
        :src="videoUrl"
        @play="onPlay"
        @pause="onPause"
        @ended="onEnded"
        @error="onError"
      >
        您的浏览器不支持视频播放
      </video>
    </div>
    <div class="video-info" v-if="videoName">
      <h3 class="video-title">{{ videoName }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VideoPlayer',
  props: {
    videoUrl: {
      type: String,
      required: true,
      default: ''
    },
    videoName: {
      type: String,
      default: ''
    },
    poster: {
      type: String,
      default: ''
    }
  },
  methods: {
    onPlay() {
      this.$emit('play');
    },
    onPause() {
      this.$emit('pause');
    },
    onEnded() {
      this.$emit('ended');
    },
    onError() {
      this.$modal.msgError('视频加载失败，请检查视频链接是否正确');
    },
    // 播放视频
    play() {
      this.$refs.videoPlayer && this.$refs.videoPlayer.play();
    },
    // 暂停视频
    pause() {
      this.$refs.videoPlayer && this.$refs.videoPlayer.pause();
    }
  }
};
</script>

<style lang="scss" scoped>
.video-player-wrapper {
  width: 100%;
  background: #000;
  border-radius: 12px;
  overflow: hidden;

  .video-player-container {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #000;

    .video-player {
      max-width: 100%;
      max-height: 70vh;
      width: auto;
      height: auto;
      outline: none;
    }
  }

  .video-info {
    padding: 16px 20px;
    background: #fff;

    .video-title {
      margin: 0;
      font-size: 16px;
      font-weight: 500;
      color: #333;
    }
  }
}
</style>

