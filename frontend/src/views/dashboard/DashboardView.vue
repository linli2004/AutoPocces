<template>
  <section class="page">
    <h1 class="page-title">运行总览</h1>
    <el-row :gutter="16">
      <el-col v-for="item in stats" :key="item.label" :span="6">
        <div class="panel stat">
          <span>{{ item.label }}</span>
          <strong>{{ item.value }}</strong>
        </div>
      </el-col>
    </el-row>
    <div class="panel panel-body flow-summary">
      <h2>开发占位</h2>
      <p>本页保留运行总览入口，后续可接入真实统计接口、运行趋势、异常提醒和待办摘要。</p>
    </div>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { listApprovalTasks } from '@/api/modules/approval'
import { listConnectors } from '@/api/modules/connector'
import { listInstances } from '@/api/modules/execution'
import { listWorkflows } from '@/api/modules/workflow'

const connectorCount = ref(0)
const workflowCount = ref(0)
const instanceCount = ref(0)
const approvalCount = ref(0)

const stats = computed(() => [
  { label: '连接器', value: connectorCount.value },
  { label: '流程定义', value: workflowCount.value },
  { label: '运行实例', value: instanceCount.value },
  { label: '待审批', value: approvalCount.value },
])

async function load() {
  const [connectors, workflows, instances, approvals] = await Promise.all([
    listConnectors(),
    listWorkflows(),
    listInstances(),
    listApprovalTasks('PENDING'),
  ])
  connectorCount.value = connectors.length
  workflowCount.value = workflows.length
  instanceCount.value = instances.length
  approvalCount.value = approvals.length
}

onMounted(load)
</script>

<style scoped>
.stat {
  display: flex;
  height: 96px;
  flex-direction: column;
  justify-content: center;
  padding: 16px;
}

.stat span {
  color: #667085;
}

.stat strong {
  margin-top: 8px;
  font-size: 30px;
}

.flow-summary {
  margin-top: 16px;
}

.flow-summary h2 {
  margin: 0 0 8px;
  font-size: 18px;
}

.flow-summary p {
  margin: 0;
  color: #475467;
}
</style>
