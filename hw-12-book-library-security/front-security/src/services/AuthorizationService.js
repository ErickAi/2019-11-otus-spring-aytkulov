import http from '../http';
import qs from 'querystring'

const client_id = 'book-library-id';
const client_secret = 'secret';

class AuthorizationService {
    token(user) {
        return http
            .post('/oauth/token', qs.stringify({
                grant_type: 'password', client_id: client_id, client_secret: client_secret,
                username: user.email, password: user.password}))
            .then(response => {
                if (response.data.access_token) {
                    localStorage.setItem('tokenInfo', JSON.stringify(response.data));
                }
                return response.data;
            })
            .catch(error => console.log(error));
    }

    userinfo() {
        return http
            .get('/userinfo')
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
        return http.post('signup', {
            name: user.name,
            email: user.email,
            password: user.password
        });
    }
}

export default new AuthorizationService();
