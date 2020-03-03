<template>
  <el-container class="mt-5">
    <el-form :model="modifiedBook" ref="bookForm" label-width="120px">
      <el-form-item
              prop="name"
              label="Name"
              :rules="{ required: true, message: 'message', trigger: 'blur' }">
<!--        <el-input v-model="bookForm.name"></el-input>-->
        <el-input v-model="modifiedBook.name"></el-input>
      </el-form-item>
      <el-form-item
              v-for="(genre, index) in addedGenres"
              :label="'Жанр: index: ' + index + '. id: ' + genre.id"
              :key="genre.id"
              :prop="'addedGenres.' + index + '.name'"
              :rules="{required: true, message: 'genre can not be null', trigger: 'blur'}">
        <el-input v-model="genre.name"></el-input>
        <el-button @click.prevent="removeGenre(genre)">Delete</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('bookForm')">Submit</el-button>
        <el-button @click="addDomain">New domain</el-button>
        <el-button @click="resetForm('bookForm')">Reset</el-button>
      </el-form-item>
    </el-form>
    <!--    БЫЛО     -->
    <!--
        <div class="el-col-10">
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
        </div>
    -->
  </el-container>
</template>

<script>
    import BookDataService from "../services/BookDataService";
    import GenreDataService from "../services/GenreDataService";

    export default {
        name: "Book",
        data() {
            return {
                currentBook: null,
                modifiedBook: null,
                addedGenres: [{
                            id: 1,
                            name: ''
                }],
                genres: [],
                // bookForm: {
                //     book: null,
                //     id: 1,
                //     name: '',
                //     author: null,
                //     genres: [{
                //         id: 1,
                //         name: ''
                //     }],
                // }
            };
        },
        methods: {
            requestBookAndSetData(id) {
                BookDataService.get(id)
                    .then(response => {
                        console.log(response.data);
                        this.currentBook = response.data;
                        this.modifiedBook = this.currentBook;
                        this.addedGenres = this.currentBook.genres;
                        // this.bookForm.id = this.currentBook.id;
                        // this.bookForm.name = this.currentBook.name;
                        // this.bookForm.author = this.currentBook.author;
                        // this.bookForm.genres = this.currentBook.genres;
                        // console.log("this.bookForm");
                        // console.log(this.bookForm)
                    })
                    .catch(e => {
                        console.log(e);
                    });

            },
            getGenres() {
                GenreDataService.getAll()
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
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm() {
                // this.$refs[formName].resetFields();
                this.modifiedBook = this.currentBook;
            },
            removeGenre(item) {
                // var index = this.bookForm.genres.indexOf(item);
                let index = this.modifiedBook.genres.indexOf(item);
                if (index !== -1) {
                    this.modifiedBook.genres.splice(index, 1);
                }
            },
            addDomain() {
                this.modifiedBook.genres.push({
                    id: Date.now(),
                    name: ''
                });
            },
        },
        mounted() {
            this.requestBookAndSetData(this.$route.params.id);
            // this.getGenres();
        }
    }
</script>

<style scoped>

</style>
