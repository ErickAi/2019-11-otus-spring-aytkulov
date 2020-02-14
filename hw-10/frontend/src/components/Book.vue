<template>
  <div class="edit-form" v-if="currentBook">
    <h4>Book</h4>
    <form>
      <div class="form-group">
        <label for="name">Name</label>
        <input class="form-control" id="name" type="text"
               v-model="currentBook.name"/>
      </div>
      <div class="form-group">
        <label for="author">Author</label>
        <input class="form-control" id="author" type="text"
               v-model="currentBook.author.name"
        />
      </div>
      <div v-bind:key="genre.id" v-for="(genre) in currentBook.genres">
        <div class="raw">
          <label for="genreName">{{ genre.id }}</label>
          <input id="genreName"
                 type="text"
                 v-bind:value=genre.name>
        </div>
      </div>
    </form>

    <button @click="deleteBook"
            class="badge badge-danger mr-2"
    >
      Delete
    </button>

    <button @click="updateBook" class="badge badge-success"
            type="submit"
    >
      Update
    </button>
    <p>{{ message }}</p>
  </div>

  <div v-else>
    <br/>
    <p>Please click on a Book...</p>
  </div>
</template>

<script>
    import BookDataService from "../services/BookDataService";
    import GenreDataService from "../services/GenreDataService";

    export default {
        name: "book",
        data() {
            return {
                currentBook: null,
                genres: []
                , count: 0,
                countDebounced: 0
            };
        },
        methods: {
            getBook(id) {
                BookDataService.get(id)
                    .then(response => {
                        this.currentBook = response.data;
                        console.log(response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },

            getGenres() {
                GenreDataService.get()
                    .then(response => {
                        this.genres = response.data;
                        console.log(response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },

            updateBook() {
                BookDataService.update(this.currentBook.id, this.currentBook)
                    .then(response => {
                        console.log(response.data);
                        this.message = 'The book was updated successfully!';
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },

            deleteBook() {
                BookDataService.delete(this.currentBook.id)
                    .then(response => {
                        console.log(response.data);
                        this.$router.push({name: "books"});
                    })
                    .catch(e => {
                        console.log(e);
                    });
            }
        },
        mounted() {
            this.getBook(this.$route.params.id);
            this.getGenres();
        }
    };
</script>

<style>
  .edit-form {
    max-width: 300px;
    margin: auto;
  }
</style>
