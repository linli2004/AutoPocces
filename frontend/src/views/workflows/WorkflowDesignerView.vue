<template>
  <section class="page">
    <div class="toolbar">
      <h1 class="page-title">流程设计</h1>
      <el-select v-model="selectedId" placeholder="选择流程" style="width: 260px" @change="selectWorkflow">
        <el-option v-for="item in workflows" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
    </div>
    <WorkflowCanvas :workflow="selectedWorkflow" />
    <div class="panel panel-body detail">
      <h2>{{ selectedWorkflow?.name || '流程画布占位' }}</h2>
      <p>{{ selectedWorkflow?.description || '后续在这里接入流程编辑、节点配置、发布等真实功能。' }}</p>
      <el-table :data="selectedWorkflow?.nodes || []" size="small">
        <el-table-column prop="name" label="节点" />
        <el-table-column prop="type" label="类型" width="180" />
        <el-table-column label="配置">
          <template #default="{ row }">
            <code>{{ row.config }}</code>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import WorkflowCanvas from '@/components/workflow/WorkflowCanvas.vue'
import { listWorkflows } from '@/api/modules/workflow'
import type { WorkflowDefinition } from '@/types/domain'

const workflows = ref<WorkflowDefinition[]>([])
const selectedId = ref('')
const selectedWorkflow = computed(() => workflows.value.find((item) => item.id === selectedId.value))

function selectWorkflow() {
  // Reserved for loading workflow detail and node configuration.
}

onMounted(async () => {
  workflows.value = await listWorkflows()
  selectedId.value = workflows.value[0]?.id || ''
})
</script>

<style scoped>
.toolbar .page-title {
  margin: 0 auto 0 0;
}

.detail {
  margin-top: 16px;
}

.detail h2 {
  margin: 0 0 8px;
  font-size: 18px;
}

.detail p {
  color: #667085;
}
</style>
