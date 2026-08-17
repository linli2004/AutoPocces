import { createRouter, createWebHistory } from 'vue-router'

import BasicLayout from '@/layouts/BasicLayout.vue'
import { useAuthStore } from '@/store/auth'

// Page routes are kept aligned with the backend module boundaries.
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/auth/LoginView.vue'),
      meta: { public: true },
    },
    {
      path: '/',
      component: BasicLayout,
      meta: { requiresAuth: true },
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

router.beforeEach(async (to) => {
  const authStore = useAuthStore()

  if (to.path === '/login') {
    if (!authStore.token) {
      return true
    }

    if (!authStore.sessionVerified) {
      try {
        await authStore.fetchCurrentUser()
      } catch {
        return true
      }
    }

    if (authStore.isAuthenticated) {
      const redirect = typeof to.query.redirect === 'string' && to.query.redirect.startsWith('/')
        ? to.query.redirect
        : '/'
      return redirect
    }

    return true
  }

  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth)
  if (!requiresAuth) {
    return true
  }

  if (!authStore.token) {
    return {
      path: '/login',
      query: { redirect: to.fullPath },
    }
  }

  if (!authStore.sessionVerified) {
    try {
      await authStore.fetchCurrentUser()
    } catch {
      return {
        path: '/login',
        query: { redirect: to.fullPath },
      }
    }
  }

  return true
})

export default router
