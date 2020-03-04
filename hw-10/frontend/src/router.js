import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    mode: "history",
    routes: [
        {
            path: "/",
            alias: "/books",
            name: "books",
            component: () => import("./components/Books")
        },

        {
            path: "/books/:id",
            name: "book-details",
            component: () => import("./components/Book")
        },

        {
            path: "/addBook",
            name: "book-details",
            component: () => import("./components/Book")
        },
    ]
});
