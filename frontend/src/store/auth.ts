import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

import { getCurrentUser, login as loginRequest } from '@/api/modules/auth'
import type { AuthUser } from '@/types/auth'
import {
  clearAuthStorage,
  getStoredToken,
  getStoredUser,
  storeToken,
  storeUser,
} from '@/utils/auth-storage'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(getStoredToken())
  const user = ref<AuthUser | null>(getStoredUser())
  const sessionVerified = ref(false)

  const isAuthenticated = computed(() => Boolean(token.value))

  async function login(username: string, password: string) {
    const result = await loginRequest({ username, password })

    token.value = result.token
    user.value = result.user
    sessionVerified.value = true

    storeToken(result.token)
    storeUser(result.user)

    return result
  }

  async function fetchCurrentUser() {
    if (!token.value) {
      clearSession()
      return null
    }

    try {
      const currentUser = await getCurrentUser()
      user.value = currentUser
      sessionVerified.value = true
      storeUser(currentUser)
      return currentUser
    } catch (error) {
      clearSession()
      throw error
    }
  }

  function clearSession() {
    token.value = ''
    user.value = null
    sessionVerified.value = false
    clearAuthStorage()
  }

  function logout() {
    clearSession()
  }

  return {
    token,
    user,
    sessionVerified,
    isAuthenticated,
    login,
    fetchCurrentUser,
    clearSession,
    logout,
  }
})
