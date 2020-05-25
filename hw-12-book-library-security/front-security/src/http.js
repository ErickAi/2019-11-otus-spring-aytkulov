import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:9090",
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
let subscribers = [];

http.interceptors.response.use(
    response => {
        return response;
    },
    err => {
        const {
            config,
            response: {status, data}
        } = err;

        const originalRequest = config;

        if (status === 401 && data.error_description.includes("Access token expired")) {
            if (!isRefreshing) {
                isRefreshing = true;
                store
                    .dispatch("refreshToken")
                    .then(({status}) => {
                        if (status === 200 || status === 204) {
                            isRefreshing = false;
                        }
                        subscribers = [];
                    })
                    .catch(error => {
                        console.error(error);
                    });
            }

            const requestSubscribers = new Promise(resolve => {
                subscribeTokenRefresh(() => {
                    resolve(axios(originalRequest));
                });
            });

            onRefreshed();

            return requestSubscribers;
        }
    }
);
function subscribeTokenRefresh(cb) {
    subscribers.push(cb);
}

function onRefreshed() {
    subscribers.map(cb => cb());
}

export default http;
