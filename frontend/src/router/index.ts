import { createRouter, createWebHistory } from 'vue-router'
import BasicLayout from '@/layouts/BasicLayout.vue'

// Page routes are kept aligned with the backend module boundaries.
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: BasicLayout,
      children: [
        { path: '', component: () => import('@/views/dashboard/DashboardView.vue') },
        { path: 'connectors', component: () => import('@/views/connectors/ConnectorListView.vue') },
        { path: 'workflows', component: () => import('@/views/workflows/WorkflowDesignerView.vue') },
        { path: 'instances', component: () => import('@/views/instances/InstanceListView.vue') },
        { path: 'approvals', component: () => import('@/views/approvals/ApprovalCenterView.vue') },
      ],
    },
  ],
})

export default router
