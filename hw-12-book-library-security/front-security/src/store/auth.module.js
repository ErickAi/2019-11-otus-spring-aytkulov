import AuthService from '../services/AuthorizationService';
export const auth = {
    state: {
        currentUser: JSON.parse(localStorage.getItem('currentUser')),
        tokenInfo: JSON.parse(localStorage.getItem('tokenInfo')),
        tokenExpiration: JSON.parse(localStorage.getItem('tokenExpiration')),
    },
    actions: {
        async login({commit}, user) {
            return AuthService.token(user)
                .then(tokenInfo => {
                        return AuthService.userinfo()
                            .then(user => {
                                    commit('LOGIN_SUCCESS', user);
                                    commit('TOKEN_SUCCESS', tokenInfo);
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
                        location.reload();
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
            let expiration = new Date();
            expiration.setSeconds(expiration.getSeconds() + tokenInfo.expires_in);
            console.log("token expires at: " + expiration);
            localStorage.setItem("tokenExpiration", expiration.getTime());
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
        tokenExpiresIn: () => {
            let expiration = new Date(JSON.parse(localStorage.getItem("tokenExpiration")));
            let now = new Date();
            if (expiration) {
                    return expiration < now ? "expired" : expiration.getSeconds() - new Date().getSeconds();
            } else {
                return null;
            }
        },
        isAdmin: state =>{
            if (state.currentUser) {
                return state.currentUser.roles.contains("ROLE_ADMIN")
            }
        }
    }
};
