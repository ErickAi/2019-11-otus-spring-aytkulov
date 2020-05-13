import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
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
            component: () => import("./views/user/Profile")
        },
        {
            path: "/",
            alias: "/books",
            name: "books",
            component: () => import("./views/Books")
        },

        {
            path: "/books/:id/details",
            name: "book-details",
            component: () => import("./views/BookDetails")
        },

        {
            path: "/books/:id/edit",
            name: "book-edit",
            component: () => import("./views/BookEdit")
        },

        {
            path: "/new-book",
            name: "book-new",
            component: () => import("./views/BookEdit")
        },
    ]
});
