import AuthService from '../services/AuthorizationService';

export const auth = {
    state: {
        currentUser: JSON.parse(localStorage.getItem('currentUser')),
        tokenInfo: JSON.parse(localStorage.getItem('tokenInfo')),
        loggedIn: false,
        tokenObtainedTime: null,
        tokenExpiresIn: null
    },
    actions: {
        async login({commit}, user) {
            return AuthService.token(user)
                .then(tokenInfo => {
                        commit('TOKEN_SUCCESS', tokenInfo);
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
        async refreshToken({commit}) {
            return AuthService.refresh()
                .then(tokenInfo => {
                        commit('TOKEN_SUCCESS', tokenInfo);
                        return Promise.resolve(tokenInfo);
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
        TOKEN_SUCCESS(state, tokenInfo) {
            state.tokenInfo = tokenInfo;
            state.tokenObtainedTime = new Date();
            console.log("tokenInfo.expires_in " + tokenInfo.expires_in);
            state.tokenExpiresIn = tokenInfo.expires_in;
        },
        LOGIN_SUCCESS(state, user) {
            state.loggedIn = true;
            state.currentUser = user;
        },
        LOGIN_FAILURE(state) {
            state.loggedIn = false;
            state.tokenInfo = null;
            state.currentUser = null;
        },
        LOGOUT(state) {
            state.loggedIn = false;
            state.tokenInfo = null;
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
        currentUser: state => {
            if (state.currentUser) {
                return state.currentUser;
            } else {
                return {};
            }
        },
        accessToken: state => {
            if (state.tokenInfo) {
                return state.tokenInfo.access_token;
            } else {
                return null;
            }
        },
    }
};
