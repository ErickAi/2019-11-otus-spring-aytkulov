import axios from 'axios';
import qs from 'querystring'

const API_URL = 'http://localhost:9090';
const config = {
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
};
const client_id = 'book-library-id';
const client_secret = 'secret';

class AuthorizationService {
    token(user) {
        return axios
            .post(API_URL + '/oauth/token', qs.stringify({
                grant_type: 'password', client_id: client_id, client_secret: client_secret,
                username: user.email, password: user.password,
            }), config)
            .then(response => {
                if (response.data.access_token) {
                    localStorage.setItem('access_token', JSON.stringify(response.data.access_token));
                }
                return response.data;
            })
            .catch(error => console.log(error));
    }

    userinfo() {
        let accessToken = JSON.parse(localStorage.getItem('access_token'));
        return axios
            .get(API_URL + '/userinfo', {headers: {'Authorization' : `Bearer ${accessToken}`}})
            .then(response => {
                if (response.data) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            })
            .catch(error => console.log(error));
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'signup', {
            name: user.name,
            email: user.email,
            password: user.password
        });
    }
}

export default new AuthorizationService();
