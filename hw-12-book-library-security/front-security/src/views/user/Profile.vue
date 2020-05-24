<template>
    <el-container>
        <nav-menu/>
        <el-main>
            <div class="profile">
                <el-card>
                    <img id="profile-img"
                         src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                         class="profile-img-card"/>
                    <p>
                        <strong>Id:</strong>
                        {{currentUser.id}}
                    </p>
                    <p>
                        <strong>Email:</strong>
                        {{currentUser.email}}
                    </p>
                        <strong>Authorities:</strong>
                    <ul>
                        <li v-for="(role,index) in currentUser.roles" :key="index">{{role}}</li>
                    </ul>
                    <el-divider/>
                    <p>
                        <strong>Access token:</strong>
                        {{truncatedToken}}
                    </p>
                </el-card>
            </div>
        </el-main>
    </el-container>
</template>

<script>
    import NavMenu from "../NavMenu";

    export default {
        name: 'Profile',
        components: {
            NavMenu
        },
        computed: {
            currentUser(){
                return this.$store.getters.currentUser
            },
            truncatedToken() {
                let currentToken = this.$store.getters.accessToken;
                console.log(currentToken);
                if (!currentToken) {
                    return "";
                }
                return currentToken.substr(0, 22) + '...{ TRUNCATED }';
            }
        },
        mounted() {
            if (!this.currentUser) {
                this.$router.push('/login');
            }
        }
    };
</script>

<style scoped>
    .profile {
        font-size: large;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .el-card {
        padding-top: 30px;
        padding-bottom: 20px;
        width: 540px;
        display: flex;
        justify-content: center;
    }

    .profile-img-card {
        width: 196px;
        height: 196px;
        margin: 0 auto 10px;
        display: block;
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
    }
</style>
