import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Autocomplete from '@trevoreyre/autocomplete-vue'
import '@trevoreyre/autocomplete-vue/dist/style.css'

Vue.config.productionTip = false;
Vue.use(Autocomplete);

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
