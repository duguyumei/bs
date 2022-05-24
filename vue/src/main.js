// import { createApp } from 'vue'
// import App from './App.vue'
// import router from './router'
// import store from './store'
//
// createApp(App).use(store).use(router).mount('#app')

// elementplus
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from "./router"
import store from "./store"
import * as ElIconModules from '@element-plus/icons'

const app = createApp(App)

// 统一注册Icon图标
for (const iconName in ElIconModules) {
    if (Reflect.has(ElIconModules, iconName)) {
        const item = ElIconModules[iconName]
        app.component(iconName, item)
    }
}
//引入中文
import zhCn from 'element-plus/es/locale/lang/zh-cn'

app.use(ElementPlus, {locale: zhCn,}).use(router).use(store)
app.mount('#app')