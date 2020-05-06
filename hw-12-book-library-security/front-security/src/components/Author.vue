<template>
  <div>
    <el-container>
      <el-form :model="currentAuthor" :rules="authorValidationRules" label-width="50px" ref="authorForm">
        <h1>Добавить автора</h1>
        <el-form-item
                class="mt-4"
                label="Имя"
                prop="name">
          <el-input
                  class="el-input-group"
                  clearable
                  v-model="currentAuthor.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="submitAuthorForm('authorForm')" type="primary">
            <span v-if="currentAuthor.id">Обновить</span><span v-else>Создать</span>
          </el-button>
          <el-button @click="resetAuthorForm('authorForm')" class="btn-edit">Новый</el-button>
        </el-form-item>
      </el-form>
    </el-container>
    <el-container>
      <el-table
              :data="computedAuthors"
              :default-sort="{prop: 'name', order: 'ascending'}"
              @current-change="handleCurrentChange"
              empty-text="Нет данных"
              highlight-current-row
              style="padding: 5px;  background-color: mintcream"
      >
        <el-table-column
                label="id"
                prop="id"
                sortable
                width="66">
        </el-table-column>
        <el-table-column
                label="Author"
                prop="name"
                sortable
                width="auto">
        </el-table-column>
      </el-table>
    </el-container>
  </div>
</template>

<script>
    import AuthorDataService from "../services/AuthorDataService";

    export default {
        name: "Author",
        data() {
            let checkNameExists = (rule, value, callback) => {
                value = value.trim();
                if (!value || value.length === 0) {
                    return callback(new Error('У автора должно быть имя'));
                }
                if (this.allAuthors.some(author => {
                    return author.name === (value)
                })) {
                    return callback(new Error('Автор с таким именем уже существует'));
                }
                callback();
            };
            return {
                currentAuthor: {
                    id: null,
                    name: ''
                },
                renderAuthors: true,
                allAuthors: [],
                authorValidationRules: {
                    name: [
                        {validator: checkNameExists, trigger: 'blur'}
                    ]
                }
            }
        },
        computed: {
            computedAuthors: function () {
                return this.allAuthors
            }
        },
        methods: {
            getAllAuthors() {
                AuthorDataService.getAll()
                    .then(response => {
                        this.allAuthors = response.data;
                        this.$root.$emit("authorsUpdated", response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },
            submitAuthorForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.currentAuthor.name.trim();
                        AuthorDataService.create(this.currentAuthor)
                            .then(response => {
                                console.log(response.data);
                                this.renderAuthors = false;
                                this.getAllAuthors();
                                this.$nextTick(() => {
                                    this.renderAuthors = true
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
            },
            resetAuthorForm(formName) {
                this.$refs[formName].resetFields();
                this.currentAuthor = {
                    id: null,
                    name: ''
                }
            },
            handleCurrentChange(val) {
                this.currentAuthor = {...val};
                window.scrollTo(0, 0);
            },
        },
        mounted() {
            this.getAllAuthors();
        }
    }
</script>

<style scoped>

</style>
