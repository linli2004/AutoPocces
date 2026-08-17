<template>
  <el-container class="layout">
    <el-aside width="228px" class="layout-aside">
      <div class="brand">
        <strong>AutoProcess</strong>
        <span>智能流程编排平台</span>
      </div>
      <el-menu router :default-active="$route.path" class="menu">
        <el-menu-item v-for="item in menus" :key="item.path" :index="item.path">
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="layout-header">
        <span class="header-title">面向异构业务系统的可扩展智能流程编排与集成平台</span>

        <el-dropdown trigger="click" @command="handleCommand">
          <div class="user-entry">
            <el-avatar :size="34" class="user-avatar">{{ avatarText }}</el-avatar>
            <div class="user-meta">
              <strong>{{ authStore.user?.displayName || authStore.user?.username || '用户' }}</strong>
              <span>{{ roleLabel }}</span>
            </div>
            <el-icon class="user-arrow"><ArrowDown /></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item disabled>{{ authStore.user?.username || '-' }}</el-dropdown-item>
              <el-dropdown-item divided command="logout">
                <el-icon><SwitchButton /></el-icon>
                退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main class="layout-main">
        <RouterView />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowDown, SwitchButton } from '@element-plus/icons-vue'

import { menus } from '@/settings/menu'
import { useAuthStore } from '@/store/auth'

const router = useRouter()
const authStore = useAuthStore()

const roleLabel = computed(() => {
  switch (authStore.user?.role) {
    case 'ADMIN':
      return '管理员'
    case 'OPERATOR':
      return '操作员'
    case 'VIEWER':
      return '只读用户'
    default:
      return authStore.user?.role || '已登录'
  }
})

const avatarText = computed(() => {
  const value = authStore.user?.displayName || authStore.user?.username || 'U'
  return value.trim().slice(0, 1).toUpperCase()
})

async function handleCommand(command: string) {
  if (command !== 'logout') {
    return
  }

  authStore.logout()
  await router.replace('/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.layout-aside {
  border-right: 1px solid #dfe5ef;
  background: #ffffff;
}

.brand {
  display: flex;
  flex-direction: column;
  gap: 4px;
  height: 72px;
  padding: 16px 20px;
  border-bottom: 1px solid #dfe5ef;
}

.brand strong {
  font-size: 18px;
}

.brand span {
  color: #667085;
  font-size: 12px;
}

.menu {
  border-right: 0;
}

.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
  border-bottom: 1px solid #dfe5ef;
  background: #ffffff;
  color: #344054;
}

.header-title {
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.user-entry {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 6px 8px;
  border-radius: 8px;
  cursor: pointer;
  outline: none;
}

.user-entry:hover {
  background: #f6f8fb;
}

.user-avatar {
  flex: 0 0 auto;
  background: #315efb;
  font-weight: 700;
}

.user-meta {
  display: flex;
  min-width: 86px;
  flex-direction: column;
  gap: 2px;
}

.user-meta strong {
  max-width: 160px;
  overflow: hidden;
  color: #344054;
  font-size: 13px;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.user-meta span {
  color: #98a2b3;
  font-size: 11px;
}

.user-arrow {
  color: #98a2b3;
  font-size: 12px;
}

.layout-main {
  padding: 0;
  background: #f6f8fb;
}
</style>
