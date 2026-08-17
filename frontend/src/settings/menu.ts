import { Checked, DataLine, Grid, Link, Tickets } from '@element-plus/icons-vue'

// Sidebar menu configuration is centralized for later permission control.
export const menus = [
  { path: '/', title: '运行总览', icon: DataLine },
  { path: '/connectors', title: '连接器', icon: Link },
  { path: '/workflows', title: '流程设计', icon: Grid },
  { path: '/instances', title: '运行实例', icon: Tickets },
  { path: '/approvals', title: '审批中心', icon: Checked },
]
