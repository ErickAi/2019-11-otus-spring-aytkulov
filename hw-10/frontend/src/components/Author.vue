<template>
  <div>
    <el-container>
      <el-form :model="currentAuthor" ref="authorForm" label-width="50px" :rules="authorValidationRules">
        <h1>Добавить автора</h1>
        <el-form-item
                class="mt-4"
                prop="name"
                label="Имя"
        >
          <el-input
                  class="el-input-group"
                  v-model="currentAuthor.name"
                  clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAuthorForm('authorForm')">
            <span v-if="currentAuthor.id">Обновить</span><span v-else>Создать</span>
          </el-button>
          <el-button type="warning" @click="resetAuthorForm('authorForm')">Новый</el-button>
        </el-form-item>
      </el-form>
    </el-container>
    <el-table
            :data="allAuthors"
            :default-sort="{prop: 'name', order: 'ascending'}"
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
              label="Author"
              prop="name"
              sortable>
      </el-table-column>
    </el-table>
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
                allAuthors: [],
                authorValidationRules: {
                    name: [
                        {validator: checkNameExists, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            getAllAuthors() {
                AuthorDataService.getAll()
                    .then(response => {
                        this.allAuthors = response.data;
                        // this.$root.$emit("authorsUpdated", response.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },
            submitAuthorForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(this.currentAuthor);
                        AuthorDataService.create(this.currentAuthor)
                            .catch(e => {
                                console.log(e);
                            });
                    } else {
                        console.log('Book form is not valid!');
                        return false;
                    }
                });
                this.getAllAuthors()
            },
            resetAuthorForm(formName) {
                this.$refs[formName].resetFields();
                this.currentAuthor = {
                    id: null,
                    name: ''
                }
            },
            handleCurrentChange(val) {
                this.currentAuthor = val;
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
