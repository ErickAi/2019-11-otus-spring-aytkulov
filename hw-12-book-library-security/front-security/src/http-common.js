import axios from "axios";

const accessToken = JSON.parse(localStorage.getItem("access_token"));

const httpCommon = axios.create({
    baseURL: "http://localhost:9090/",
    headers: {
        "Content-type": "application/json",
    }
});

httpCommon.defaults.headers.common['Authorization'] = accessToken ? `Bearer ${accessToken}` : null;

export default httpCommon;
