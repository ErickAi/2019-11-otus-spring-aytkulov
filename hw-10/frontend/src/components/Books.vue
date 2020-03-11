<template>
  <el-container>
    <nav-menu/>
    <el-main>
      <div>
        <el-row :gutter="24">
          <el-col :span="6">
            <el-input
                    placeholder="Найти по названию"
                    size="mini"
                    v-model="search.name"
                    v-on:input="searchData('name')"/>
          </el-col>
          <el-col :span="6">
            <el-input
                    placeholder="Найти по автору"
                    size="mini"
                    v-model="search.author"
                    v-on:input="searchData('author.name')"/>
          </el-col>
          <el-col :span="6">
            <el-input
                    placeholder="Найти по жанру"
                    size="mini"
                    v-model="search.genre"
                    v-on:input="searchData('genres.name')"/>
          </el-col>
        </el-row>
        <el-container>
          <el-table
                  :data="filteredBooks"
                  :default-sort="{prop: 'name', order: 'ascending'}"
                  @current-change="handleCurrentChange"
                  empty-text="Нет книг, удовлетворяющих поиску"
                  highlight-current-row
          >
            <el-table-column
                    label="Название"
                    prop="name"
                    sortable>
            </el-table-column>
            <el-table-column
                    label="Автор"
                    prop="author.name"
                    sortable>
            </el-table-column>
            <el-table-column
                    :formatter="formatGenres"
                    label="Жанр"
                    prop="genres"
            >
            </el-table-column>
          </el-table>
        </el-container>
      </div>
    </el-main>
  </el-container>
</template>

<script>
    import BookDataService from "@/services/BookDataService";
    import NavMenu from "@/components/NavMenu";
    import Fuse from "fuse.js"

    export default {
        data() {
            return {
                books: [],
                filteredBooks: [],
                currentBook: null,
                search: {
                    name: null,
                    author: null,
                    genre: null
                },
            }
        },
        methods: {
            retrieveBooks() {
                BookDataService.getAll()
                    .then(response => {
                        this.books = response.data;
                        this.filteredBooks = this.books;
                        console.log(response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },
            searchData(columnName) {
                console.log(this.search);
                console.log(columnName);
                let options = {
                    threshold: 0.2,
                    keys: [columnName],
                };
                let fuse = new Fuse(this.books, options);
                let searchString = '';
                switch (columnName) {
                    case 'name':
                        searchString = this.search.name;
                        break;
                    case 'author.name':
                        searchString = this.search.author;
                        break;
                    case 'genres.name':
                        searchString = this.search.genre;
                        break;
                    default:
                        searchString = "";
                }
                this.filteredBooks = searchString ? fuse.search(searchString) : this.books;

            },
            handleCurrentChange(val) {
                this.currentBook = val;
                this.$router.push("/books/" + val.id)
            },
            formatGenres(row) {
                let allGenreNames = [];
                row.genres.forEach(genre => allGenreNames.push(genre.name));
                return allGenreNames.join(", ")
            }
        },
        components: {
            NavMenu
        },
        mounted() {
            this.retrieveBooks();
        }
    }
</script>
