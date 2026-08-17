<template>
  <div class="login-page">
    <section class="login-intro">
      <div class="intro-content">
        <div class="brand-mark">AP</div>
        <p class="eyebrow">AUTOPROCESS</p>
        <h1>让业务流程连接、判断、审批与执行。</h1>
        <p class="intro-copy">
          面向异构业务系统的可扩展智能流程编排与集成平台。
        </p>
        <div class="intro-points">
          <span>连接器管理</span>
          <span>可视化流程编排</span>
          <span>运行与审批追踪</span>
        </div>
      </div>
    </section>

    <section class="login-panel">
      <div class="login-card">
        <div class="login-heading">
          <p class="eyebrow">WELCOME BACK</p>
          <h2>登录 AutoProcess</h2>
          <p>使用系统账号进入流程管理平台。</p>
        </div>

        <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-position="top"
          size="large"
          @keyup.enter="submit"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="username"
              placeholder="请输入用户名"
              :prefix-icon="User"
              clearable
            />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
              v-model="form.password"
              type="password"
              autocomplete="current-password"
              placeholder="请输入密码"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>

          <el-button
            class="login-button"
            type="primary"
            size="large"
            :loading="submitting"
            @click="submit"
          >
            登录
          </el-button>
        </el-form>

        <p class="login-tip">登录状态由 JWT 验证，Token 失效后会自动返回登录页。</p>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Lock, User } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'

import { useAuthStore } from '@/store/auth'

interface LoginForm {
  username: string
  password: string
}

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const formRef = ref<FormInstance>()
const submitting = ref(false)
const form = reactive<LoginForm>({
  username: '',
  password: '',
})

const rules: FormRules<LoginForm> = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { max: 64, message: '用户名不能超过 64 个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { max: 128, message: '密码不能超过 128 个字符', trigger: 'blur' },
  ],
}

async function submit() {
  if (submitting.value) {
    return
  }

  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) {
    return
  }

  submitting.value = true
  try {
    await authStore.login(form.username, form.password)

    ElMessage.success('登录成功')

    const redirect = typeof route.query.redirect === 'string' && route.query.redirect.startsWith('/')
      ? route.query.redirect
      : '/'

    await router.replace(redirect)
  } catch (error) {
    const message = error instanceof Error ? error.message : '登录失败，请稍后重试'
    ElMessage.error(message)
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: minmax(0, 1.08fr) minmax(420px, 0.92fr);
  background: #f5f7fb;
}

.login-intro {
  position: relative;
  display: flex;
  align-items: center;
  overflow: hidden;
  padding: 72px clamp(48px, 7vw, 112px);
  background:
    radial-gradient(circle at 18% 18%, rgba(77, 118, 255, 0.26), transparent 32%),
    linear-gradient(145deg, #111a2f 0%, #172442 50%, #1c315d 100%);
  color: #fff;
}

.login-intro::after {
  content: '';
  position: absolute;
  right: -140px;
  bottom: -160px;
  width: 420px;
  height: 420px;
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 50%;
  box-shadow:
    0 0 0 64px rgba(255, 255, 255, 0.025),
    0 0 0 128px rgba(255, 255, 255, 0.02);
}

.intro-content {
  position: relative;
  z-index: 1;
  max-width: 620px;
}

.brand-mark {
  width: 52px;
  height: 52px;
  display: grid;
  place-items: center;
  margin-bottom: 34px;
  border: 1px solid rgba(255, 255, 255, 0.22);
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.08);
  font-size: 17px;
  font-weight: 800;
  letter-spacing: 0.08em;
  backdrop-filter: blur(8px);
}

.eyebrow {
  margin: 0 0 12px;
  color: #7d9cff;
  font-size: 12px;
  font-weight: 800;
  letter-spacing: 0.18em;
}

.login-intro h1 {
  max-width: 580px;
  margin: 0;
  font-size: clamp(38px, 4vw, 60px);
  line-height: 1.12;
  letter-spacing: -0.035em;
}

.intro-copy {
  max-width: 520px;
  margin: 24px 0 0;
  color: rgba(255, 255, 255, 0.72);
  font-size: 16px;
  line-height: 1.8;
}

.intro-points {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 40px;
}

.intro-points span {
  padding: 8px 12px;
  border: 1px solid rgba(255, 255, 255, 0.14);
  border-radius: 999px;
  color: rgba(255, 255, 255, 0.78);
  font-size: 12px;
}

.login-panel {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px;
  background: #fff;
}

.login-card {
  width: min(100%, 420px);
}

.login-heading {
  margin-bottom: 32px;
}

.login-heading h2 {
  margin: 0;
  color: #172033;
  font-size: 30px;
  letter-spacing: -0.02em;
}

.login-heading > p:last-child {
  margin: 10px 0 0;
  color: #7a8498;
  line-height: 1.6;
}

.login-button {
  width: 100%;
  margin-top: 8px;
}

.login-tip {
  margin: 24px 0 0;
  color: #98a2b3;
  font-size: 12px;
  line-height: 1.6;
  text-align: center;
}

@media (max-width: 900px) {
  .login-page {
    grid-template-columns: 1fr;
  }

  .login-intro {
    min-height: 300px;
    padding: 48px 32px;
  }

  .login-intro h1 {
    font-size: 36px;
  }

  .intro-points {
    margin-top: 28px;
  }

  .login-panel {
    padding: 44px 24px 56px;
  }
}
</style>
