<template>
  <el-container>
    <nav-menu/>
    <el-main>
      <el-row>
        <el-container class="el-col-11">
          <el-form :model="currentBook" label-width="120px" ref="bookForm">
            <h1>Редактировать книгу</h1>
            <el-form-item
                    :rules="{ required: true, message: 'У книги должно быть название.', trigger: 'blur' }"
                    class="mt-4"
                    label="Название"
                    prop="name">
              <el-input class="el-input-group" v-model="currentBook.name"></el-input>
            </el-form-item>
            <el-form-item
                    :rules="{ required: true, message: 'message', trigger: 'blur' }"
                    label="Автор"
                    prop="author">
              <el-input class="el-input-group" readonly v-model="currentBook.author.name"></el-input>
              <el-autocomplete
                      :fetch-suggestions="querySearchAsyncAuthor"
                      @select="handleSelectAuthor"
                      class="el-input-group mt-1"
                      clearable
                      placeholder="Выберите из списка"
                      v-model="authorSuggestion"
                      value-key="name"
              ></el-autocomplete>
            </el-form-item>
            <div>
              <el-form-item
                      :key="genre.id"
                      :prop="`genres.${index}.name`"
                      :v-bind="index"
                      label="Жанр"
                      v-for="(genre, index) in currentBook.genres">
                <el-input class="el-input-group" readonly v-model="genre.name"></el-input>
                <el-button @click.prevent="removeGenre(genre)" class="mt-1 right btn-delete" round>
                  Удалить
                </el-button>
              </el-form-item>
            </div>
            <el-form-item
                    label="Добавить жанр"
                    prop="genreForAdd">
              <el-autocomplete
                      :fetch-suggestions="querySearchAsyncGenres"
                      @select="handleSelectGenre"
                      class="el-input-group"
                      clearable
                      placeholder="Выберите из списка"
                      v-model="genreSuggestion"
                      value-key="name"
              ></el-autocomplete>
            </el-form-item>
            <el-form-item>
              <el-button @click="submitBookForm('bookForm')" type="primary">
                <span v-if="currentBook.id">Обновить</span><span v-else>Создать</span>
              </el-button>
              <el-button @click="resetForm('bookForm')" class="btn-edit">Сбросить</el-button>
              <el-button @click="deleteBook" class="btn-delete">Удалить</el-button>
            </el-form-item>
          </el-form>
        </el-container>
        <author class="el-col-6"/>
        <genre class="el-col-6"/>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
    import NavMenu from "../views/NavMenu";
    import Author from "../views/Author";
    import Genre from "../views/Genre";
    import BookDataService from "../services/BookDataService";

    export default {
        name: "BookEdit",
        components: {
            NavMenu,
            Author,
            Genre
        },
        data() {
            return {
                isNew: true,
                currentBook: {
                    id: null,
                    name: "",
                    author: {
                        id: null,
                        name: ""
                    },
                    genres: []
                },
                allAuthors: [],
                allGenres: [],
                authorSuggestion: null,
                genreSuggestion: null,
            };
        },
        created() {
            this.$root.$on("authorsUpdated", (authors) => {
                this.allAuthors = authors
            });
            this.$root.$on("genresUpdated", (genres) => {
                this.allGenres = genres
            })
        },
        methods: {
            resetForm() {
                if (this.$route.params.id) {
                    this.requestBookAndSetData(this.$route.params.id);
                    this.isNew = false;
                } else {
                    this.currentBook = {
                        id: null,
                        name: "",
                        author: {
                            id: null,
                            name: ""
                        },
                        genres: []
                    }
                }
            },
            requestBookAndSetData(id) {
                BookDataService.get(id)
                    .then(response => {
                        console.log("Book received. id: " + id);
                        this.currentBook = response.data;
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
            },
            submitBookForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let bookId = this.currentBook.id;
                        (bookId ? BookDataService.update(bookId, this.currentBook)
                            : BookDataService.create(this.currentBook))
                            .then(response => {
                                this.message = 'The book was updated successfully!';
                                console.log(response.data);
                            })
                            .catch(e => {
                                console.log(e);
                            });
                    } else {
                        console.log('Book form is not valid!');
                        return false;
                    }
                });
            },
            removeGenre(item) {
                let index = this.currentBook.genres.indexOf(item);
                if (index !== -1) {
                    this.currentBook.genres.splice(index, 1);
                }
            },
            querySearchAsyncAuthor(queryString, cb) {
                let authors = this.allAuthors;
                let suggestions = queryString ? authors.filter(this.createFilter(queryString)) : authors;
                cb(suggestions);
            },
            handleSelectAuthor(author) {
                this.currentBook.author = author;
                console.log(this.currentBook);
            },
            querySearchAsyncGenres(queryString, cb) {
                let genres = this.allGenres;
                let suggestions = queryString ? genres.filter(this.createFilter(queryString)) : genres;
                cb(suggestions);
            },
            handleSelectGenre(genre) {
                this.currentBook.genres.push(genre);
                console.log(this.currentBook);
            },
            createFilter(queryString) {
                return (object) => {
                    return (object.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
        },
        mounted() {
            this.resetForm();
        }
    }
</script>

<style>
</style>
