import request from '@/utils/request'
import type { WorkflowDefinition, WorkflowInstance } from '@/types/domain'

// Workflow APIs are used for designer, publishing, and runtime triggering.
export function listWorkflows() {
  return request.get<unknown, WorkflowDefinition[]>('/workflows')
}

export function getWorkflow(id: string) {
  return request.get<unknown, WorkflowDefinition>(`/workflows/${id}`)
}

export function publishWorkflow(id: string) {
  return request.post<unknown, WorkflowDefinition>(`/workflows/${id}/publish`)
}

export function triggerWorkflow(id: string, payload: Record<string, unknown>) {
  return request.post<unknown, WorkflowInstance>(`/workflows/${id}/trigger`, { payload })
}

export function listNodeTypes() {
  return request.get<unknown, Array<Record<string, unknown>>>('/workflows/node-types')
}
