import request from '@/utils/request'
import type { Connector, ConnectorAction } from '@/types/domain'

// 连接器接口用于展示外部系统及其可执行动作。
export function listConnectors() {
  return request.get<unknown, Connector[]>('/connectors')
}

export function listConnectorActions(connectorId: string) {
  return request.get<unknown, ConnectorAction[]>(`/connectors/${connectorId}/actions`)
}

export function testConnectorAction(connectorId: string, actionKey: string, input: Record<string, unknown>) {
  return request.post<unknown, Record<string, unknown>>(`/connectors/${connectorId}/actions/${actionKey}/test`, { input })
}
