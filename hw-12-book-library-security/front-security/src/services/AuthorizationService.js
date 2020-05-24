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
                    localStorage.setItem('tokenInfo', JSON.stringify(response.data));
                }
                return response.data;
            })
            .catch(error => console.log(error));
    }

    userinfo() {
        let tokenInfo = JSON.parse(localStorage.getItem('tokenInfo'));
        return axios
            .get(API_URL + '/userinfo', {headers: {'Authorization' : `Bearer ${tokenInfo.access_token}`}})
            .then(response => {
                if (response.data) {
                    localStorage.setItem('currentUser', JSON.stringify(response.data));
                }
                return response.data;
            })
            .catch(error => console.log(error));
    }

    logout() {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('access_token');
        localStorage.removeItem('tokenInfo');
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
