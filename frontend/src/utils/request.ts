import axios from 'axios'

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

request.interceptors.response.use((response) => {
  // 后端统一返回 code/message/data，这里把 data 解包给页面使用。
  const body = response.data as ApiResponse<unknown>
  if (body && typeof body.code === 'number') {
    if (body.code !== 0) {
      return Promise.reject(new Error(body.message || '请求失败'))
    }
    return body.data
  }
  return response.data
})

export default request
