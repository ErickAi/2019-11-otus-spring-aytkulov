<template>
  <div>
    <el-container>
      <el-form :model="currentGenre" :rules="genreValidationRules" class="genreForm" label-width="80px" ref="genreForm">
        <h1>Добавить жанр</h1>
        <el-form-item
                class="mt-4"
                label="Название"
                prop="name">
          <el-input
                  class="el-input-group"
                  clearable
                  v-model="currentGenre.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="submitGenreForm('genreForm')" type="primary">
            <span v-if="currentGenre.id">Обновить</span><span v-else>Создать</span>
          </el-button>
          <el-button @click="resetGenreForm('genreForm')" class="btn-edit">Новый</el-button>
        </el-form-item>
      </el-form>
    </el-container>
    <el-container>
      <el-table
              :data="computedGenres"
              :default-sort="{prop: 'name', order: 'ascending'}"
              @current-change="handleCurrentChange"
              empty-text="Нет данных"
              highlight-current-row
              style="padding: 5px; background-color: mintcream"
      >
        <el-table-column
                label="id"
                prop="id"
                sortable
                width="66">
        </el-table-column>
        <el-table-column
                label="Genre"
                prop="name"
                sortable
                width="auto">
        </el-table-column>
      </el-table>
    </el-container>
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
                renderGenres: true,
                allGenres: [],
                genreValidationRules: {
                    name: [
                        {validator: checkNameExists, trigger: 'blur'}
                    ]
                }

            }
        },
        computed: {
            computedGenres: function () {
                return this.allGenres
            }
        },
        methods: {
            getAllGenres() {
                GenreDataService.getAll()
                    .then(response => {
                        this.allGenres = response.data;
                        this.$root.$emit("genresUpdated", response.data);
                        console.log(response.data)
                    })
                    .catch(e => {
                        console.log(e);
                    });
            }
            ,
            submitGenreForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.currentGenre.name.trim();
                        GenreDataService.create(this.currentGenre)
                            .then(response => {
                                console.log(response.data);
                                this.renderGenres = false;
                                this.getAllGenres();
                                this.$nextTick(() => {
                                    this.renderGenres = true
                                })
                            })
                            .catch(e => {
                                console.log(e);
                            });
                    } else {
                        console.log('Book form is not valid!');
                        return false;
                    }
                });
            }
            ,
            resetGenreForm(formName) {
                this.$refs[formName].resetFields();
                this.currentGenre = {
                    id: null,
                    name: ''
                }
            }
            ,
            handleCurrentChange(val) {
                this.currentGenre = {...val};
                window.scrollTo(0, 0);
            }
            ,
        }
        ,
        mounted() {
            this.getAllGenres();
        }
    }
</script>

<style scoped>

</style>
