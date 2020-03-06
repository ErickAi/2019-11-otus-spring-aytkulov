<template>
  <el-container>
    <nav-menu/>
    <el-main>
      <el-row>
        <el-container class="el-col-12">
          <el-form :model="currentBook" ref="bookForm" label-width="120px">
            <h1>Редактировать книгу</h1>
            <el-form-item
                    class="mt-4"
                    prop="name"
                    label="Название"
                    :rules="{ required: true, message: 'У книги должно быть название.', trigger: 'blur' }">
              <el-input class="el-input-group" v-model="currentBook.name"></el-input>
            </el-form-item>
            <el-form-item
                    prop="author"
                    label="Автор"
                    :rules="{ required: true, message: 'message', trigger: 'blur' }">
              <el-input class="el-input-group" v-model="currentBook.author.name" readonly></el-input>
              <el-autocomplete
                      class="el-input-group mt-1"
                      value-key="name"
                      v-model="authorSuggestion"
                      :fetch-suggestions="querySearchAsyncAuthor"
                      placeholder="Выберите из списка"
                      @select="handleSelectAuthor"
                      clearable
              ></el-autocomplete>
            </el-form-item>
            <div v-if="Array.isArray(this.currentBook.genres) && this.currentBook.genres.length">
              <el-form-item
                      v-for="(genre, index) in currentBook.genres"
                      label="Жанр"
                      :v-bind="index"
                      :key="genre.id"
                      :prop="`genres.${index}.name`">
                <el-input class="el-input-group" v-model="genre.name" readonly></el-input>
                <el-button class="mt-1 right" type="danger" round size="mini" @click.prevent="removeGenre(genre)">
                  Удалить
                </el-button>
              </el-form-item>
            </div>
            <el-form-item
                    prop="genreForAdd"
                    label="Добавить жанр">
              <el-autocomplete
                      class="el-input-group"
                      value-key="name"
                      v-model="genreSuggestion"
                      :fetch-suggestions="querySearchAsyncGenres"
                      placeholder="Выберите из списка"
                      @select="handleSelectGenre"
                      clearable
              ></el-autocomplete>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitBookForm('bookForm')">
                <span v-if="currentBook.id">Обновить</span><span v-else>Создать</span>
              </el-button>
              <el-button type="warning" @click="resetForm('bookForm')">Сбросить</el-button>
              <el-button type="danger" @click="deleteBook">Удалить</el-button>
            </el-form-item>
          </el-form>
        </el-container>
          <author class="el-col-6"/>
          <genre  class="el-col-6"/>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
    import NavMenu from "@/components/NavMenu";
    import Author from "@/components/Author";
    import Genre from "@/components/Genre";
    import BookDataService from "../services/BookDataService";

    export default {
        name: "Book",
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
        created(){
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
