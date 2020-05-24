import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:9090",
});

http.interceptors.request.use(
    config => {
        if(config.url.includes("token")){
            config.headers["Content-type"] = "application/x-www-form-urlencoded";
        }else {
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
export default http;
