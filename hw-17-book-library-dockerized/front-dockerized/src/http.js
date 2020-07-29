import axios from "axios";

const http = axios.create({
    baseURL: window.location.origin + '/api'
});

http.interceptors.request.use(
    config => {
        if (config.url.includes("token")) {
            config.headers["Content-type"] = "application/x-www-form-urlencoded";
        } else {
            let tokenInfo = JSON.parse(localStorage.getItem("tokenInfo"));
            config.headers["Authorization"] = "bearer " + tokenInfo.access_token;
            config.headers["Content-type"] = "application/json";
        }
        return config;
    },
    error => {
        Promise.reject(error);
    }
);
import store from "./store";
let isRefreshing = false;

http.interceptors.response.use(
    response => {
        return response;
    },
    err => {
        const {
            response: {status, data}
        } = err;

        if (status === 401 && data.error_description.includes("Access token expired")) {
            if (!isRefreshing) {
                isRefreshing = true;
                store
                    .dispatch("refreshToken")
                    .then(({status}) => {
                        if (status === 200 || status === 204) {
                            isRefreshing = false;
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
            }
        }
    }
);

export default http;
