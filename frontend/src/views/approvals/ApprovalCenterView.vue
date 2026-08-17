<template>
  <section class="page">
    <div class="toolbar">
      <h1 class="page-title">审批中心</h1>
      <el-button @click="load">刷新</el-button>
    </div>
    <div class="panel panel-body">
      <el-table :data="tasks">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="assignee" label="审批人" width="140" />
        <el-table-column prop="status" label="状态" width="140" />
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button size="small" type="primary" :disabled="row.status !== 'PENDING'" @click="approve(row.id)">通过</el-button>
            <el-button size="small" :disabled="row.status !== 'PENDING'" @click="reject(row.id)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { approveTask, listApprovalTasks, rejectTask } from '@/api/modules/approval'
import type { ApprovalTask } from '@/types/domain'

const tasks = ref<ApprovalTask[]>([])

// 审批中心展示所有审批任务，实际项目中可按当前登录人过滤。
async function load() {
  tasks.value = await listApprovalTasks()
}

async function approve(id: string) {
  // 审批通过后，后端流程引擎会从审批节点之后继续执行。
  const instance = await approveTask(id, '同意')
  ElMessage.success(`审批通过，实例状态：${instance.status}`)
  await load()
}

async function reject(id: string) {
  // 审批拒绝后，流程实例会进入 REJECTED 状态。
  const instance = await rejectTask(id, '拒绝')
  ElMessage.warning(`审批拒绝，实例状态：${instance.status}`)
  await load()
}

onMounted(load)
</script>

<style scoped>
.toolbar .page-title {
  margin: 0 auto 0 0;
}
</style>
