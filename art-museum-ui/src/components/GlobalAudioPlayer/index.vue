<template>
  <transition name="el-fade-in">
    <div
      v-if="visible && audioUrl && track"
      class="global-audio-player"
      :style="{ top: posY + 'px', left: posX + 'px' }"
    >
      <div class="gap-top"></div>
      <div
        class="player-main"
        @mousedown.stop.prevent="onDragStart"
      >
        <div class="player-close" @click.stop="closePlayer">
          <i class="el-icon-close"></i>
        </div>

        <div class="player-top">
          <div class="player-left">
            <i class="el-icon-headset player-icon"></i>
            <div class="player-info">
              <div class="player-title" :title="track.name || '未命名音效'">
                {{ track.name || '未命名音效' }}
              </div>
              <div
                v-if="track.userName"
                class="player-user"
                :title="track.userName"
              >
                {{ track.userName }}
              </div>
            </div>
          </div>
        </div>

        <div class="player-center">
          <div class="player-progress" @click="seek($event)">
            <div class="player-progress-inner">
              <div
                class="player-progress-bar"
                :style="{ width: progress + '%' }"
              ></div>
            </div>
            <div class="player-time">
              <span>{{ formatTime(currentTime) }}</span>
              <span>{{ formatTime(duration) }}</span>
            </div>
          </div>
        </div>

        <div class="player-bottom">
          <el-button
            class="player-prev-btn"
            icon="el-icon-arrow-left"
            circle
            @click.stop="playPrev"
          />
          <el-button
            type="primary"
            circle
            class="player-btn"
            @click.stop="togglePlay"
          >
            <i :class="isPlaying ? 'el-icon-video-pause' : 'el-icon-video-play'"></i>
          </el-button>
          <el-button
            class="player-next-btn"
            icon="el-icon-d-arrow-right"
            circle
            @click.stop="playNextRandom"
          />
        </div>
      </div>

      <audio
        ref="audio"
        :src="audioUrl"
        @timeupdate="onTimeUpdate"
        @loadedmetadata="onLoadedMetadata"
        @ended="onEnded"
      ></audio>
    </div>
  </transition>
</template>

<script>
import audioBus from "@/utils/audioBus";

export default {
  name: "GlobalAudioPlayer",
  data() {
    return {
      visible: false,
      track: null,
      audioUrl: "",
      isPlaying: false,
      currentTime: 0,
      duration: 0,
      progress: 0,
      // 拖动位置
      posX: 0,
      posY: 80,
      dragging: false,
      dragOffsetX: 0,
      dragOffsetY: 0,
      // 当前列表（由页面传入，用于随机切歌）
      playlist: [],
      // 播放历史（用于上一曲）
      history: [],
      historyIndex: -1,
    };
  },
  created() {
    audioBus.$on("play", this.startPlay);
    audioBus.$on("updatePlaylist", this.updatePlaylist);
  },
  beforeDestroy() {
    audioBus.$off("play", this.startPlay);
    audioBus.$off("updatePlaylist", this.updatePlaylist);
    this.removeDragListeners();
  },
  methods: {
    updatePlaylist(list) {
      if (Array.isArray(list)) {
        this.playlist = list.filter(item => item && item.fileUrl);
      } else {
        this.playlist = [];
      }
    },
    startPlay(payload) {
      this.internalPlay(payload, true);
    },
    internalPlay(payload, addToHistory) {
      if (!payload || !payload.fileUrl) {
        this.$message.warning("没有可播放的音效文件");
        return;
      }
      const firstFile = payload.fileUrl.split(",")[0];
      const url = this.getFilePath(firstFile);
      this.track = {
        id: payload.id,
        name: payload.name,
        userName: payload.userName,
      };
      this.audioUrl = url;
      this.visible = true;
      this.currentTime = 0;
      this.duration = 0;
      this.progress = 0;
      if (addToHistory) {
        // 如果之前回退过，先截断未来分支
        if (this.historyIndex >= 0 && this.historyIndex < this.history.length - 1) {
          this.history = this.history.slice(0, this.historyIndex + 1);
        }
        this.history.push({
          id: payload.id,
          name: payload.name,
          userName: payload.userName,
          fileUrl: payload.fileUrl,
        });
        this.historyIndex = this.history.length - 1;
      }
      this.$nextTick(() => {
        // 首次显示时初始化到右上角
        if (!this.posX) {
          const el = this.$el;
          if (el) {
            const rect = el.getBoundingClientRect();
            const marginRight = 24;
            this.posX = window.innerWidth - rect.width - marginRight;
          }
        }
        const audio = this.$refs.audio;
        if (audio) {
          audio.play();
          this.isPlaying = true;
        }
      });
    },
    togglePlay() {
      const audio = this.$refs.audio;
      if (!audio) return;
      if (this.isPlaying) {
        audio.pause();
        this.isPlaying = false;
      } else {
        audio.play();
        this.isPlaying = true;
      }
    },
    onTimeUpdate(e) {
      const audio = e.target;
      this.currentTime = audio.currentTime || 0;
      this.duration = audio.duration || 0;
      if (this.duration > 0) {
        this.progress = (this.currentTime / this.duration) * 100;
      } else {
        this.progress = 0;
      }
    },
    onLoadedMetadata(e) {
      const audio = e.target;
      this.duration = audio.duration || 0;
    },
    onEnded() {
      this.isPlaying = false;
      this.currentTime = 0;
      this.progress = 0;
      // 播放结束后自动随机切一首（如果有列表）
      if (this.playlist.length > 1) {
        this.playNextRandom();
      }
    },
    seek(event) {
      const audio = this.$refs.audio;
      if (!audio || !this.duration) return;
      const rect = event.currentTarget.getBoundingClientRect();
      const ratio = (event.clientX - rect.left) / rect.width;
      const time = this.duration * Math.min(Math.max(ratio, 0), 1);
      audio.currentTime = time;
    },
    formatTime(seconds) {
      if (!seconds || isNaN(seconds)) return "00:00";
      const m = Math.floor(seconds / 60);
      const s = Math.floor(seconds % 60);
      const mm = m < 10 ? "0" + m : "" + m;
      const ss = s < 10 ? "0" + s : "" + s;
      return mm + ":" + ss;
    },
    playPrev() {
      if (!this.history || this.history.length <= 1 || this.historyIndex <= 0) {
        this.$message.warning("没有上一首了");
        return;
      }
      this.historyIndex -= 1;
      const prev = this.history[this.historyIndex];
      if (prev) {
        this.internalPlay(prev, false);
      }
    },
    playNextRandom() {
      if (!this.playlist || this.playlist.length === 0) {
        this.$message.warning("当前没有可播放的音效列表");
        return;
      }
      let candidates = this.playlist;
      if (this.track && this.playlist.length > 1) {
        candidates = this.playlist.filter(item => item.id !== this.track.id);
      }
      const next = candidates[Math.floor(Math.random() * candidates.length)];
      if (next) {
        this.internalPlay(next, true);
      }
    },
    closePlayer() {
      const audio = this.$refs.audio;
      if (audio) {
        audio.pause();
      }
      this.visible = false;
      this.isPlaying = false;
      this.currentTime = 0;
      this.duration = 0;
      this.progress = 0;
    },
    onDragStart(e) {
      const el = this.$el;
      if (!el) return;
      const rect = el.getBoundingClientRect();
      // 当前组件左上角位置
      this.posX = rect.left;
      this.posY = rect.top;
      this.dragging = true;
      this.dragOffsetX = e.clientX - this.posX;
      this.dragOffsetY = e.clientY - this.posY;
      document.addEventListener("mousemove", this.onDragging);
      document.addEventListener("mouseup", this.onDragEnd);
    },
    onDragging(e) {
      if (!this.dragging) return;
      const viewportWidth = window.innerWidth;
      const viewportHeight = window.innerHeight;
      const el = this.$el;
      const rect = el ? el.getBoundingClientRect() : { width: 360, height: 100 };
      let x = e.clientX - this.dragOffsetX;
      let y = e.clientY - this.dragOffsetY;
      const margin = 8;
      // 边界限制
      x = Math.max(margin, Math.min(viewportWidth - rect.width - margin, x));
      y = Math.max(margin, Math.min(viewportHeight - rect.height - margin, y));
      this.posX = x;
      this.posY = y;
    },
    onDragEnd() {
      this.dragging = false;
      this.removeDragListeners();
    },
    removeDragListeners() {
      document.removeEventListener("mousemove", this.onDragging);
      document.removeEventListener("mouseup", this.onDragEnd);
    },
  },
};
</script>

<style scoped>
.global-audio-player {
  position: fixed;
  z-index: 999;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  pointer-events: none;
}

.gap-top {
  height: 0;
}

.player-main {
  pointer-events: auto;
  min-width: 340px;
  max-width: 460px;
  background: radial-gradient(circle at top left, #3a8ee6 0%, #1f2d3d 40%, #101827 100%);
  border-radius: 14px;
  box-shadow: 0 14px 40px rgba(0, 0, 0, 0.45);
  padding: 12px 16px 12px;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 6px;
  color: #fff;
  position: relative;
}

.player-top {
  display: flex;
  align-items: center;
  margin-bottom: 2px;
}

.player-close {
  position: absolute;
  top: 2px;
  right: 2px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #c0c4cc;
  font-size: 12px;
}

.player-close:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #ffffff;
}

.player-left {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 170px;
}

.player-icon {
  font-size: 24px;
  color: #ffd04b;
  filter: drop-shadow(0 0 4px rgba(255, 208, 75, 0.8));
}

.player-info {
  display: flex;
  flex-direction: column;
  max-width: 230px;
}

.player-title {
  font-size: 14px;
  font-weight: 600;
  color: #fff;
  margin-bottom: 2px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.player-user {
  font-size: 12px;
  color: #c0c4cc;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.player-center {
  flex: 1;
  min-width: 0;
  margin-bottom: 4px;
}

.player-progress {
  cursor: pointer;
}

.player-progress-inner {
  width: 100%;
  height: 4px;
  background: rgba(255, 255, 255, 0.16);
  border-radius: 999px;
  overflow: hidden;
  margin-bottom: 4px;
}

.player-progress-bar {
  height: 100%;
  background-image: linear-gradient(90deg, #ffd04b, #ff9f43);
  border-radius: 999px;
  transition: width 0.2s ease;
}

.player-time {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #e5e9f2;
}

.player-right {
  display: flex;
  align-items: center;
}

.player-btn {
  border: none;
  background: #ffd04b;
  color: #303133;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.4);
}

.player-btn:hover {
  background: #ffdd75;
}

.player-btn i {
  font-size: 18px;
}

.player-bottom {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.player-prev-btn {
  border-radius: 50%;
  border: 1px solid rgba(255, 255, 255, 0.35);
  background: transparent;
  color: #fff;
  padding: 4px;
}

.player-prev-btn:hover {
  background: rgba(255, 255, 255, 0.12);
}

.player-next-btn {
  margin-left: 6px;
  border-radius: 50%;
  border: 1px solid rgba(255, 255, 255, 0.35);
  background: transparent;
  color: #fff;
  padding: 4px;
}

.player-next-btn:hover {
  background: rgba(255, 255, 255, 0.12);
}

.global-audio-player audio {
  display: none;
}
</style>


