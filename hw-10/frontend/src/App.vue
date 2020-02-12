<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a class="navbar-brand" href="#">Library</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link" href="/books">Books</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/add">Add</a>
        </li>
      </div>
    </nav>

    <div class="container mt-3">
      <router-view/>
    </div>
  </div>
</template>

<script>
    export default {
        name: 'App',
    }
</script>
