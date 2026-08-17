import request from '@/utils/request'
import type { AuthUser, LoginRequest, LoginResponse } from '@/types/auth'

export function login(data: LoginRequest) {
  return request.post<unknown, LoginResponse>('/auth/login', data)
}

export function getCurrentUser() {
  return request.get<unknown, AuthUser>('/auth/me')
}
