<template>
  <div>
    <nav-menu/>
    <el-main class="is-justify-center" direction="horizontal">
      <el-container class="is-justify-center">
        <el-table
                class="el-col-20"
                :data="books.filter(data => !searchByName || data.name.toLowerCase().includes(searchByName.toLowerCase()))"
                highlight-current-row
                @current-change="handleCurrentChange"
        >
          <el-table-column
                  label="Name"
                  prop="name"
                  sortable>
          </el-table-column>
          <el-table-column
                  label="Author"
                  prop="author.name"
                  sortable>
          </el-table-column>
          <el-table-column
                  label="Genres"
                  prop="genres"
                  :formatter="formatGenres"
          >
          </el-table-column>
          <el-table-column
                  align="right">
            <template slot="header" slot-scope="scope">
              <el-input
                      v-model="searchByName"
                      size="mini"
                      placeholder="Type to search"/>
            </template>
          </el-table-column>
        </el-table>
      </el-container>
    </el-main>
  </div>
</template>

<script>
    import BookDataService from "@/services/BookDataService";
    import NavMenu from "@/components/NavMenu";

    export default {
        data() {
            return {
                books: [],
                search: "",
                currentBook: null,
                searchByName: "",
                searchByAuthor: "",
                searchByGenre: "",
            }
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
            setCurrent(row) {
                this.$refs.singleTable.setCurrentRow(row);
            },
            handleCurrentChange(val) {
                this.currentBook = val;
                this.$router.push("/books/" + val.id)
            },
            formatGenres(row) {
                let allGenreNames = [];
                row.genres.forEach(genre => allGenreNames.push(genre.name))
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
