import type { AuthUser } from '@/types/auth'

const TOKEN_KEY = 'autoprocess.token'
const USER_KEY = 'autoprocess.user'

export function getStoredToken(): string {
  return localStorage.getItem(TOKEN_KEY) ?? ''
}

export function storeToken(token: string) {
  localStorage.setItem(TOKEN_KEY, token)
}

export function removeStoredToken() {
  localStorage.removeItem(TOKEN_KEY)
}

export function getStoredUser(): AuthUser | null {
  const value = localStorage.getItem(USER_KEY)
  if (!value) {
    return null
  }

  try {
    return JSON.parse(value) as AuthUser
  } catch {
    localStorage.removeItem(USER_KEY)
    return null
  }
}

export function storeUser(user: AuthUser) {
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}

export function removeStoredUser() {
  localStorage.removeItem(USER_KEY)
}

export function clearAuthStorage() {
  removeStoredToken()
  removeStoredUser()
}
