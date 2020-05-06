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
            path: "/books/:id/details",
            name: "book-details",
            component: () => import("./components/BookDetails")
        },

        {
            path: "/books/:id/edit",
            name: "book-edit",
            component: () => import("./components/BookEdit")
        },

        {
            path: "/new-book",
            name: "book-new",
            component: () => import("./components/BookEdit")
        },
    ]
});
