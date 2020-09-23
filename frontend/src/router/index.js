import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: () => import('@/views/Home')
        },
        {
            path: '/auth',
            component: () => import('@/views/Auth')
        },
        {
            path: '/register',
            component: () => import('@/views/Register')
        },
        {
            path: '/products',
            component: () => import('@/views/Products')
        },
        {
            path: '/product/:id',
            component: () => import('@/views/Product')
        },
        {
            path: '/cart',
            component: () => import('@/views/Cart')
        },
        {
            path: '/receipt',
            component: () => import('@/views/Receipt')
        },
        {
            path: '/cabinet',
            component: () => import('@/views/Cabinet')
        },
        {
            path: '/search',
            component: () => import('@/views/Search')
        },
        {
            path: '/admin/auth',
            component: () => import('@/views/AdminAuth')
        },
        {
            path: '/admin/cabinet',
            component: () => import('@/views/AdminCabinet')
        },
        {
            path: '/admin/receipt/:id(\\d{6})',
            component: () => import('@/views/AdminReceipt')
        }
    ]
})