import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
	server: {
		port: 3000, // Development server port
		proxy: {
			"/api": {
				target: "http://localhost:8080",
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api/, ""),
			},
		},
	},
	resolve: {
		alias: {
			"@": "/src", // Alias for src directory
		},
	},
});
