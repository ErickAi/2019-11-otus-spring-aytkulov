import axios from "axios";

const httpCommon = axios.create({
    baseURL: "http://localhost:9090/",
    headers: {
        "Content-type": "application/json",
    }
});

httpCommon.interceptors.request.use(
    config => {
        let tokenInfo = JSON.parse(localStorage.getItem("tokenInfo"));
        config.headers["Authorization"] = "bearer " + tokenInfo.access_token;
        return config;
    },
    error => {
        Promise.reject(error);
    }
);
export default httpCommon;
