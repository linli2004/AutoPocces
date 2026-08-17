// Frontend domain types mirror backend response fields.
export interface Connector {
  id: string
  name: string
  type: string
  baseUrl: string
  authType: string
  enabled: boolean
}

export interface ConnectorAction {
  id: string
  connectorId: string
  name: string
  actionKey: string
  method: string
  path: string
  inputSchema: string
  outputSchema: string
}

export interface WorkflowNode {
  id: string
  type: string
  name: string
  config: Record<string, unknown>
}

export interface WorkflowEdge {
  id: string
  source: string
  target: string
  condition?: string
}

export interface WorkflowDefinition {
  id: string
  workflowKey: string
  name: string
  description: string
  status: string
  version: number
  nodes: WorkflowNode[]
  edges: WorkflowEdge[]
}

export interface WorkflowInstance {
  id: string
  workflowId: string
  status: string
  currentNodeId?: string
  context: Record<string, unknown>
  errorMessage?: string
}

export interface ApprovalTask {
  id: string
  instanceId: string
  nodeId: string
  title: string
  assignee: string
  status: string
  formData: Record<string, unknown>
}
