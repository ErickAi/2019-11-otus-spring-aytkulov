<template>
  <div class="list row">
    <div class="col-md-8">
      <div class="input-group mb-3">
        <input class="form-control" placeholder="Search by author" type="text"
               v-model="authorName"/>
        <div class="input-group-append">
          <button @click="searchAuthor" class="btn btn-outline-secondary"
                  type="button"
          >
            Search
          </button>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <h4>Book List</h4>
      <ul class="list-group">
        <li :class="{ active: index === currentIndex }"
            :key="index"
            @click="setActiveBook(book, index)"
            class="list-group-item"
            v-for="(book, index) in books"
        >
          {{ book.name }}
        </li>
      </ul>
    </div>
    <div class="col-md-6">
      <div v-if="currentBook">
        <h4>Book</h4>
        <div>
          <strong>Author:</strong> {{ currentBook.author.name }}
        </div>
        <div>
          <label><strong>Genres as array:</strong></label>
          <ul>
            <li :key="genre.id"
                v-for="(genre) in this.currentBookGenres">
              {{ genre.name }}
            </li>
          </ul>
        </div>
        <a :href="'/books/' + currentBook.id"
           class="badge badge-warning"
        >
          Edit
        </a>
      </div>
      <div v-else>
        <br/>
        <p>Please click on a Book...</p>
      </div>
    </div>
  </div>
</template>

<script>
    import BookDataService from "../services/BookDataService";

    export default {
        name: "books-list",
        data() {
            return {
                books: [],
                currentBook: null,
                currentIndex: -1,
                currentBookGenres: [],
                authorName: ""
            };
        },
        methods: {
            retrieveBooks() {
                BookDataService.getAll()
                    .then(response => {
                        this.books = response.data;
                        console.log(response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },

            refreshList() {
                this.retrieveBooks();
                this.currentBook = null;
                this.currentIndex = -1;
            },

            setActiveBook(book, index) {
                this.currentBook = book;
                this.currentIndex = index;
                this.currentBookGenres = book.genres;
            },

            removeAllBooks() {
                BookDataService.deleteAll()
                    .then(response => {
                        console.log(response.data);
                        this.refreshList();
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },

            searchAuthor() {
                BookDataService.findByAuthor(this.authorName)
                    .then(response => {
                        this.books = response.data;
                        console.log(response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            }
        },
        mounted() {
            this.retrieveBooks();
        }
    };
</script>

<style>
  .list {
    text-align: left;
    max-width: 750px;
    margin: auto;
  }
</style>
