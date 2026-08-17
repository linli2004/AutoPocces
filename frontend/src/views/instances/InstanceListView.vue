<template>
  <section class="page">
    <h1 class="page-title">运行实例</h1>
    <el-row :gutter="16">
      <el-col :span="14">
        <div class="panel panel-body">
          <el-table :data="instances" highlight-current-row @current-change="select">
            <el-table-column prop="id" label="实例ID" min-width="220" />
            <el-table-column prop="status" label="状态" width="150" />
            <el-table-column prop="currentNodeId" label="当前节点" width="150" />
          </el-table>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="panel panel-body">
          <h2>节点日志</h2>
          <el-timeline>
            <el-timeline-item v-for="log in logs" :key="String(log.id)" :type="log.status === 'FAILED' ? 'danger' : 'success'">
              <strong>{{ log.nodeName }}</strong>
              <p>{{ log.status }}</p>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
    </el-row>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { listInstanceLogs, listInstances } from '@/api/modules/execution'
import type { WorkflowInstance } from '@/types/domain'

const instances = ref<WorkflowInstance[]>([])
const logs = ref<Array<Record<string, unknown>>>([])

// 运行实例列表用于观察流程是否完成、等待审批或失败。
async function load() {
  instances.value = await listInstances()
}

async function select(instance?: WorkflowInstance) {
  // 点击实例后加载节点日志，便于定位每一步输入输出。
  logs.value = instance ? await listInstanceLogs(instance.id) : []
}

onMounted(load)
</script>

<style scoped>
h2 {
  margin: 0 0 12px;
  font-size: 18px;
}
</style>
