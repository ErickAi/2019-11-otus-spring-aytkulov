<template>
  <div id="app">
    <router-view/>
    <link href="https://cdn.jsdelivr.net/npm/vuetify@1.2.2/dist/vuetify.min.css" rel="stylesheet"/>
  </div>
</template>

<script>
    export default {
        name: 'App',
    }
</script>
<style>
  @import url("//unpkg.com/element-ui@2.13.0/lib/theme-chalk/index.css");

  .el-main {
    margin-top: 64px !important;
  }

  .btn-edit {
    color: ghostwhite;
    background-color: burlywood;
  }

  .btn-delete {
    color: ghostwhite;
    background-color: lightcoral;
  }
</style>

