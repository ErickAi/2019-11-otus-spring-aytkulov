<template>
  <div>
    <el-container>
      <el-form :model="currentGenre" ref="genreForm" class="genreForm" label-width="80px" :rules="genreValidationRules">
        <h1>Добавить жанр</h1>
        <el-form-item
                class="mt-4"
                prop="name"
                label="Название">
          <el-input
                  class="el-input-group"
                  v-model="currentGenre.name"
                  clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitGenreForm('genreForm')">
            <span v-if="currentGenre.id">Обновить</span><span v-else>Создать</span>
          </el-button>
          <el-button type="warning" @click="resetGenreForm('genreForm')">Новый</el-button>
        </el-form-item>
      </el-form>
    </el-container>
    <el-table
            :data="allGenres"
            :default-sort = "{prop: 'name', order: 'ascending'}"
            highlight-current-row
            @current-change="handleCurrentChange"
            empty-text="Нет данных"
    >
      <el-table-column
              width="66"
              label="id"
              prop="id"
              sortable>
      </el-table-column>
      <el-table-column
              width="auto"
              label="Genre"
              prop="name"
              sortable>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
    import GenreDataService from "../services/GenreDataService";

    export default {
        name: "Genre",
        data() {
            let checkNameExists = (rule, value, callback) => {
                if (!value || value.length === 0 || !value.trim()) {
                    return callback(new Error('У жанра должно быть название'));
                }
                if (this.allGenres.some(genre => {
                    return genre.name === (this.currentGenre.name.trim())
                })) {
                    return callback(new Error('Жанр с таким названием уже существует'));
                }
                callback();
            };
            return {
                currentGenre: {
                    id: null,
                    name: ''
                },
                allGenres: [],
                genreValidationRules: {
                    name: [
                        {validator: checkNameExists, trigger: 'blur'}
                    ]
                }

            }
        },
        methods: {
            getAllGenres() {
                GenreDataService.getAll()
                    .then(response => {
                        this.allGenres = response.data;
                        this.$root.$emit("genresUpdated", response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },
            submitGenreForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.currentGenre.name.trim();
                        GenreDataService.create(this.currentGenre)
                            .then(response => {
                                console.log(response.data);
                            })
                            .catch(e => {
                                console.log(e);
                            });
                        this.resetGenreForm(formName);
                        this.getAllGenres()
                    } else {
                        console.log('Book form is not valid!');
                        return false;
                    }
                });
            },
            resetGenreForm(formName){
                this.$refs[formName].resetFields();
                this.currentGenre = {
                    id: null,
                    name: ''
                }
            },
            handleCurrentChange(val) {
                this.currentGenre = val;
                window.scrollTo(0,0);
            },
        },
        mounted() {
            this.getAllGenres();
        }
    }
</script>

<style scoped>

</style>
