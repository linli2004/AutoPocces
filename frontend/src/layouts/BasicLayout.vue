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
        <span>面向异构业务系统的可扩展智能流程编排与集成平台</span>
      </el-header>
      <el-main class="layout-main">
        <RouterView />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { menus } from '@/settings/menu'

// 基础布局只负责导航和页面容器，具体业务由 views 下的页面承接。
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
  border-bottom: 1px solid #dfe5ef;
  background: #ffffff;
  color: #344054;
}

.layout-main {
  padding: 0;
  background: #f6f8fb;
}
</style>
