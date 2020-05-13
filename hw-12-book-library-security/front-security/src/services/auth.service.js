import axios from 'axios';

const API_URL = 'http://localhost:9090/';

class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'signin', {
        username: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
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

export default new AuthService();
