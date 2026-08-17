<template>
  <section class="page">
    <h1 class="page-title">连接器</h1>
    <el-row :gutter="16">
      <el-col :span="8" v-for="connector in connectors" :key="connector.id">
        <div class="panel panel-body connector-card">
          <div>
            <h2>{{ connector.name }}</h2>
            <p>{{ connector.type }} · {{ connector.baseUrl }}</p>
          </div>
          <el-tag :type="connector.enabled ? 'success' : 'info'">{{ connector.enabled ? '启用' : '停用' }}</el-tag>
          <el-table :data="actions[connector.id] || []" size="small">
            <el-table-column prop="name" label="动作" />
            <el-table-column prop="method" label="方法" width="80" />
            <el-table-column prop="path" label="路径" />
          </el-table>
        </div>
      </el-col>
    </el-row>
  </section>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { listConnectorActions, listConnectors } from '@/api/modules/connector'
import type { Connector, ConnectorAction } from '@/types/domain'

const connectors = ref<Connector[]>([])
const actions = reactive<Record<string, ConnectorAction[]>>({})

// Load connectors first, then load actions for the selected connector.
onMounted(async () => {
  connectors.value = await listConnectors()
  await Promise.all(connectors.value.map(async (connector) => {
    actions[connector.id] = await listConnectorActions(connector.id)
  }))
})
</script>

<style scoped>
.connector-card {
  min-height: 240px;
}

.connector-card h2 {
  margin: 0;
  font-size: 18px;
}

.connector-card p {
  margin: 6px 0 12px;
  color: #667085;
  font-size: 13px;
}
</style>
