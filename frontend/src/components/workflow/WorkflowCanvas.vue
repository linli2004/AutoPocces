<template>
  <div class="canvas-wrap">
    <VueFlow :nodes="flowNodes" :edges="flowEdges" fit-view-on-init>
      <template #node-default="{ data }">
        <div class="flow-node">
          <strong>{{ data.label }}</strong>
          <span>{{ data.type }}</span>
        </div>
      </template>
    </VueFlow>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { VueFlow } from '@vue-flow/core'
import type { WorkflowDefinition } from '@/types/domain'

const props = defineProps<{ workflow?: WorkflowDefinition }>()

// Default node positions are only used to keep the empty canvas readable.
const positions: Record<string, { x: number; y: number }> = {
  start: { x: 40, y: 120 },
  queryErp: { x: 230, y: 120 },
  amountIf: { x: 430, y: 120 },
  approval: { x: 640, y: 40 },
  autoPass: { x: 640, y: 200 },
  updateErp: { x: 850, y: 120 },
  email: { x: 1050, y: 120 },
  end: { x: 1240, y: 120 },
}

const flowNodes = computed(() =>
  // 后端流程节点转换为 Vue Flow 节点。
  (props.workflow?.nodes || []).map((node) => ({
    id: node.id,
    type: 'default',
    position: positions[node.id] || { x: 80, y: 80 },
    data: { label: node.name, type: node.type },
  })),
)

const flowEdges = computed(() =>
  // 后端流程连线转换为 Vue Flow 连线，condition 用作分支标签。
  (props.workflow?.edges || []).map((edge) => ({
    id: edge.id,
    source: edge.source,
    target: edge.target,
    label: edge.condition || '',
    animated: true,
  })),
)
</script>

<style scoped>
.canvas-wrap {
  height: 420px;
  border: 1px solid #dfe5ef;
  border-radius: 8px;
  background: #ffffff;
}

.flow-node {
  display: flex;
  min-width: 128px;
  flex-direction: column;
  gap: 4px;
  padding: 10px 12px;
  border: 1px solid #98a2b3;
  border-radius: 8px;
  background: #ffffff;
  box-shadow: 0 6px 18px rgb(16 24 40 / 8%);
}

.flow-node span {
  color: #667085;
  font-size: 12px;
}
</style>
