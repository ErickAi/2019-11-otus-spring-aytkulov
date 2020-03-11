<template>
  <el-header style="position: fixed; width: 100%; z-index: 1;">
    <el-menu
            :router=true
            active-text-color="#ffd04b"
            background-color="#545c64"
            mode="horizontal"
            text-color="#fff"
    >
      <el-menu-item index="/">Библиотека</el-menu-item>
      <el-menu-item index="/books">Список книг</el-menu-item>
    </el-menu>
  </el-header>
</template>

<script>
    export default {}
</script>
<style>
</style>
