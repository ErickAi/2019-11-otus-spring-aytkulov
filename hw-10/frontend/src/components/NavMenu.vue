<template>
  <el-header style="position: fixed; width: 100%; z-index: 1;">
    <el-menu
            :router=true
            mode="horizontal"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
    >
      <el-menu-item index="1">Библиотека</el-menu-item>
      <el-menu-item index="/books">Список книг</el-menu-item>
      <el-menu-item index="/addBook">Добавить книгу</el-menu-item>
    </el-menu>
  </el-header>
</template>

<script>
    export default {}
</script>
<style>
</style>
