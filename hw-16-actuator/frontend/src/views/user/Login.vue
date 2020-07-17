<template>
    <el-container>
        <nav-menu/>
        <el-main>
            <div class="login">
                <el-card>
                    <img id="profile-img"
                         src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                         class="profile-img-card"/>
                    <el-form :model="user" :rules="rules" ref="loginForm">
                        <el-form-item class="mt-5" prop="email">
                            <el-input v-model="user.email" placeholder="e-mail"
                                      prefix-icon="el-icon-user-solid"></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input v-model="user.password" placeholder="password"
                                      prefix-icon="el-icon-lock"
                                      type="password" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button :loading="loading" style="width: 100%" type="primary" block
                                       @click="submitForm('loginForm')">Submit
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </div>
        </el-main>
    </el-container>
</template>
<script>
    import NavMenu from "../NavMenu";
    import User from '../../models/User';

    export default {
        name: "Login",
        components: {
            NavMenu
        },
        data() {
            let validateEmail = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please input your email'));
                } else {
                    if (this.loginForm.password !== '') {
                        this.$refs.loginForm.validateField('email');
                    }
                    callback();
                }
            };
            let validatePassword = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('Please input the password'));
                } else {
                    if (this.loginForm.password !== '') {
                        this.$refs.loginForm.validateField('password');
                    }
                    callback();
                }
            };
            return {
                user: new User('', ''),
                loading: false,
                loginForm: {
                    email: '',
                    password: '',
                },
                rules: {
                    email: [
                        {validator: validateEmail, trigger: 'blur'}
                    ],
                    password: [
                        {validator: validatePassword, trigger: 'blur'}
                    ],
                }
            };
        },
        created() {
            if (this.$store.getters.isLoggedIn) {
                this.$router.push('/profile');
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let user = this.user;
                        this.$store.dispatch('login', user)
                            .then(() => this.$router.push('/books'))
                            .catch(err => console.log(err));
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
        }
    }
</script>

<style scoped>
    .login {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .login .el-card {
        padding-top: 30px;
        padding-bottom: 20px;
        width: 340px;
        display: flex;
        justify-content: center;
    }
    .profile-img-card {
        width: 96px;
        height: 96px;
        margin: 0 auto 10px;
        display: block;
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
    }
</style>
