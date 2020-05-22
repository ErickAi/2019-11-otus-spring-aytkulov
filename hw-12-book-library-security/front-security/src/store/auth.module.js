import AuthService from '../services/AuthorizationService';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
    ? {loggedIn: true, user}
    : {loggedIn: false, user: null};

export const auth = {
    state: {
        initialState
    },
    actions: {
        async login({commit}, user) {
            return AuthService.token(user)
                .then(() => {
                        return AuthService.userinfo()
                            .then(user => {
                                    commit('loginSuccess', user);
                                    return Promise.resolve(user);
                                },
                                error => {
                                    commit('loginFailure');
                                    return Promise.reject(error);
                                }
                            )
                    },
                    error => {
                        commit('loginFailure');
                        return Promise.reject(error);
                    });
        },
        logout({commit}) {
            AuthService.logout();
            commit('logout');
        },
        register({commit}, user) {
            return AuthService.register(user).then(
                response => {
                    commit('registerSuccess');
                    return Promise.resolve(response.data);
                },
                error => {
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        }
    },
    mutations: {
        loginSuccess(state, user) {
            state.loggedIn = true;
            state.user = user;
        },
        loginFailure(state) {
            state.loggedIn = false;
            state.user = null;
        },
        logout(state) {
            state.loggedIn = false;
            state.user = null;
        },
        registerSuccess(state) {
            state.loggedIn = false;
        },
        registerFailure(state) {
            state.loggedIn = false;
        }
    },
    getters: {
        isLoggedIn: state => {
            return state.loggedIn;
        },
        accessToken: () => {
            let access_token = JSON.parse(localStorage.getItem('access_token'));
            if (access_token) {
                return access_token;
            } else {
                return {};
            }
        },
        currentUser: () => {
            let currentUser = JSON.parse(localStorage.getItem('user'));
            if (currentUser) {
                return currentUser;
            } else {
                return {};
            }
        }
    }
};
