import request from '@/utils/request'
import type { ApprovalTask, WorkflowInstance } from '@/types/domain'

// 审批接口用于查询待办，并把通过/拒绝结果反馈给流程引擎。
export function listApprovalTasks(status?: string) {
  return request.get<unknown, ApprovalTask[]>('/approval-tasks', { params: { status } })
}

export function approveTask(id: string, comment: string) {
  return request.post<unknown, WorkflowInstance>(`/approval-tasks/${id}/approve`, { comment })
}

export function rejectTask(id: string, comment: string) {
  return request.post<unknown, WorkflowInstance>(`/approval-tasks/${id}/reject`, { comment })
}
