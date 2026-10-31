import { createApp } from 'vue'

import App from './App.vue'
import router from './router'
import { TrevorismAuth } from '@trevorism/ui-auth'
import VueClickAway from "vue3-click-away";
import { createVuestic } from 'vuestic-ui'
import config from '../vuestic.config.js'
import './style.css'
import '@trevorism/ui-header-bar/style.css'

const app = createApp(App)
app.use(router)
app.use(TrevorismAuth, { router })
app.use(VueClickAway);
app.use(createVuestic({config}));
app.mount('#app')