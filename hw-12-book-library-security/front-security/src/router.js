import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router =  new Router({
    mode: "history",
    routes: [
        {
            path: "/login",
            name: "login",
            component: () => import("./views/user/Login")
        },
        {
            path: "/register",
            name: "register",
            component: () => import("./views/user/Register")
        },
        {
            path: "/profile",
            name: "profile",
            component: () => import("./views/user/Profile"),
            meta: { requiresAuth: true }
            },
        {
            path: "/",
            alias: "/books",
            name: "books",
            component: () => import("./views/Books"),
            meta: { requiresAuth: true }
        },

        {
            path: "/books/:id/details",
            name: "book-details",
            component: () => import("./views/BookDetails"),
            meta: { requiresAuth: true }
        },

        {
            path: "/books/:id/edit",
            name: "book-edit",
            component: () => import("./views/BookEdit"),
            meta: { requiresAuth: true }
        },

        {
            path: "/new-book",
            name: "book-new",
            component: () => import("./views/BookEdit"),
            meta: { requiresAuth: true }
        },
    ]
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // этот путь требует авторизации, проверяем залогинен ли
        // пользователь, и если нет, перенаправляем на страницу логина
        if (!JSON.parse(localStorage.getItem('currentUser'))) {
            next({
                path: '/login',
                query: { redirect: to.fullPath }
            })
        } else {
            next()
        }
    } else {
        next() // всегда так или иначе нужно вызвать next()!
    }
});

export default router
