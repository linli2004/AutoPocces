import axios from 'axios'

import { clearAuthStorage, getStoredToken } from '@/utils/auth-storage'

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

const request = axios.create({
  // 后端接口已约定不使用 /api 前缀，开发环境由 Vite 按路径代理到后端。
  baseURL: '',
  timeout: 10000,
})

request.interceptors.request.use((config) => {
  const token = getStoredToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

function handleUnauthorized() {
  clearAuthStorage()

  if (window.location.pathname === '/login') {
    return
  }

  const redirect = `${window.location.pathname}${window.location.search}${window.location.hash}`
  window.location.replace(`/login?redirect=${encodeURIComponent(redirect)}`)
}

request.interceptors.response.use(
  (response) => {
    // 后端统一返回 code/message/data，这里把 data 解包给页面使用。
    const body = response.data as ApiResponse<unknown>
    if (body && typeof body.code === 'number') {
      if (body.code !== 0) {
        if (body.code === 401) {
          handleUnauthorized()
        }
        return Promise.reject(new Error(body.message || '请求失败'))
      }
      return body.data
    }
    return response.data
  },
  (error: unknown) => {
    if (axios.isAxiosError(error)) {
      const status = error.response?.status
      const body = error.response?.data as Partial<ApiResponse<unknown>> | undefined

      if (status === 401 || body?.code === 401) {
        handleUnauthorized()
      }

      return Promise.reject(new Error(body?.message || error.message || '请求失败'))
    }

    return Promise.reject(error)
  },
)

export default request
