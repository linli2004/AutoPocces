export type UserRole = 'ADMIN' | 'OPERATOR' | 'VIEWER' | string
export type UserStatus = 'ENABLED' | 'DISABLED' | string

export interface AuthUser {
  id: string
  username: string
  displayName: string
  role: UserRole
  status: UserStatus
}

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  token: string
  tokenType: string
  user: AuthUser
}
