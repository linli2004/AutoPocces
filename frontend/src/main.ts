import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@vue-flow/core/dist/style.css'
import './assets/styles/base.css'
import App from './App.vue'
import router from './router'

// 前端入口：统一挂载路由、状态管理和 Element Plus 组件库。
createApp(App).use(createPinia()).use(router).use(ElementPlus).mount('#app')
