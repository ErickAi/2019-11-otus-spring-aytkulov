import AuthService from '../services/AuthorizationService';

export const auth = {
    state: {
        currentUser:  JSON.parse(localStorage.getItem('currentUser')),
        loggedIn: false
    },
    actions: {
        async login({commit}, user) {
            return AuthService.token(user)
                .then(() => {
                        return AuthService.userinfo()
                            .then(user => {
                                    commit('LOGIN_SUCCESS', user);
                                    return Promise.resolve(user);
                                },
                                error => {
                                    commit('LOGIN_FAILURE');
                                    return Promise.reject(error);
                                }
                            )
                    },
                    error => {
                        commit('LOGIN_FAILURE');
                        return Promise.reject(error);
                    });
        },
        logout({commit}) {
            AuthService.logout();
            commit('LOGOUT');
        },
        register({commit}, user) {
            return AuthService.register(user).then(
                response => {
                    commit('REGISTER_SUCCESS');
                    return Promise.resolve(response.data);
                },
                error => {
                    commit('REGISTER_FAILURE');
                    return Promise.reject(error);
                }
            );
        }
    },
    mutations: {
        LOGIN_SUCCESS(state, user) {
            state.loggedIn = true;
            state.currentUser = user;
        },
        LOGIN_FAILURE(state) {
            state.loggedIn = false;
            state.currentUser = null;
        },
        LOGOUT(state) {
            state.loggedIn = false;
            state.currentUser = null;
        },
        REGISTER_SUCCESS(state) {
            state.loggedIn = false;
        },
        REGISTER_FAILURE(state) {
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
                return null;
            }
        },
        currentUser: state => {
            if (state.currentUser) {
                return state.currentUser;
            } else {
                return {};
            }
        }
    }
};
