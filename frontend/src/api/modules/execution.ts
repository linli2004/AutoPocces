import request from '@/utils/request'
import type { WorkflowInstance } from '@/types/domain'

// 运行实例接口用于流程监控页面展示实例列表和节点日志。
export function listInstances() {
  return request.get<unknown, WorkflowInstance[]>('/workflow-instances')
}

export function listInstanceLogs(instanceId: string) {
  return request.get<unknown, Array<Record<string, unknown>>>(`/workflow-instances/${instanceId}/node-logs`)
}
