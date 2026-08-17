import { fileURLToPath, URL } from 'node:url'
import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vite'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    proxy: {
      '/connectors': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/connector-actions': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/workflows': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/workflow-instances': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/approval-tasks': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/events': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
