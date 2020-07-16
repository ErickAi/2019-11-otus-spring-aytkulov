<template>
  <el-container>
    <nav-menu/>
    <el-main>
      <el-row :gutter="20" style="padding-bottom: 20px">
        <el-col :span="6">
          <el-image
                  :fit="'cover'"
                  :src="url"
                  style="width: 220px; height: 280px; padding-left: 20px"
          >
            <div class="image-slot" slot="placeholder">
              Loading<span class="dot">...</span>
            </div>
            <div class="image-slot" slot="error">
              <i class="el-icon-picture-outline" style="font-size: 66px;"></i>
            </div>
          </el-image>
        </el-col>
        <el-col :span="18">
          <h1 style="padding-bottom: 11px; font-size: 33px; color: #3346c4">{{ currentBook.name }}</h1>
          <h3 style="padding-bottom: 11px; font-size: 23px; color: #385dff">{{ currentBook.author.name }}</h3>
          <h4 :key="genre.id"
              :prop="`genres.${index}.name`"
              :v-bind="index"
              style="padding-left: 20px; font-size: 19px"
              v-for="(genre, index) in currentBook.genres"
          ><span>{{ genre.name }}</span></h4>
        </el-col>
      </el-row>
      <el-container>
        <el-col :span="24">
          <h1>Отзывы</h1>
          <div class="comments-block">
            <vue-custom-scrollbar
                    :settings="scrollSettings"
                    class="comment-list"
                    v-infinite-scroll="loadNextCommentsPage">
              <p class="comment-item comment-entry" v-if="!bookComments.length">Ваш комментарий будет первым.</p>
              <div :key="comment.id" class="comment-item"
                   v-for="(comment) in bookComments">
                <el-row>
                  <el-col>
                    <div v-text="comment.user.name"></div>
                    <div class="comment-entry mt-2" v-text="comment.entry"></div>
                  </el-col>
                </el-row>
                <el-row>
                  <div style="float: right">
                    <el-button v-if="isModifyCommentAllowed(comment)" @click="clickEdit(comment)"
                               circle class="btn-edit" icon="el-icon-edit" size="mini"></el-button>
                    <el-button v-if="isDeleteCommentAllowed(comment)" @click="deleteComment(comment, 'commentForm')"
                               circle icon="el-icon-delete" size="mini" type="danger"></el-button>
                  </div>
                </el-row>
              </div>
            </vue-custom-scrollbar>
            <h1 class="mt-4" style="width: 100%">
              <span v-if="currentComment.id">Редактировать</span><span v-else>Добавить</span> отзыв</h1>
            <el-container class="mt-1">
              <el-form :model="currentComment" ref="commentForm" style="width: 100%">
                <el-input
                        :autosize="{ minRows: 2}"
                        class="mt-2"
                        placeholder="Ваш отзыв"
                        type="textarea"
                        v-model="currentComment.entry">
                </el-input>
                <el-form-item class="mt-1" style="position: absolute; right: 22px;">
                  <el-button v-if="currentComment.entry"
                             @click="submitCommentForm('commentForm')"
                             type="primary">Принять</el-button>
                  <el-button v-else disabled type="primary">Принять</el-button>
                  <el-button @click="resetCommentForm('commentForm')" class="btn-edit">Очистить</el-button>
                </el-form-item>
              </el-form>
            </el-container>
          </div>
        </el-col>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
    import vueCustomScrollbar from 'vue-custom-scrollbar'
    import NavMenu from "../views/NavMenu";
    import BookDataService from "../services/BookDataService";
    import CommentDataService from "../services/CommentDataService";

    const infiniteScroll = require('vue-infinite-scroll');

    export default {
        name: "BookDetails",
        components: {
            vueCustomScrollbar,
            NavMenu,
        },
        directives: {infiniteScroll},
        data() {
            return {
                currentBook: {
                    id: null,
                    name: "",
                    author: {
                        id: null,
                        name: ""
                    },
                    genres: []
                },
                bookComments: [],
                url: 'undefined',
                currentComment: {
                    id: null,
                    book: null,
                    user: null,
                    entry: "",
                },
                scrollSettings: {
                    maxScrollbarLength: 60
                }
            };
        },
        computed: {
        },

        methods: {
            initData(id) {
                BookDataService.get(id)
                    .then(response => {
                        this.currentBook = response.data;
                    })
                    .catch(e => {
                        console.log(e);
                    });
                this.loadComments(id);
            },
            loadComments(bookId) {
                CommentDataService.findByBookId(bookId)
                    .then(response => {
                        this.bookComments = response.data;
                    })
                    .catch(e => {
                        console.log(e);
                    });
            },
            loadNextCommentsPage() {
                //    need implement pagination into backend
            },
            isModifyCommentAllowed: function (comment) {
                let authorizedUser = this.$store.getters.currentUser;
                return authorizedUser.id === comment.user.id;
            },
            isDeleteCommentAllowed: function (comment) {
                let authorizedUser = this.$store.getters.currentUser;
                let isOwner = authorizedUser.id === comment.user.id;
                let isAdmin = authorizedUser.roles.indexOf("ROLE_ADMIN") > -1;
                return isOwner || isAdmin;
            },
            deleteComment(comment, formName) {
                let index = this.bookComments.indexOf(comment);
                CommentDataService.delete(comment.id);
                this.bookComments.splice(index, 1);
                this.resetCommentForm(formName)
            },
            submitCommentForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let commentId = this.currentComment.id;
                        this.currentComment.book = this.currentBook;
                        this.currentComment.user = this.$store.getters.currentUser;
                        commentId ? CommentDataService.update(commentId, this.currentComment)
                            : CommentDataService.create(this.currentComment)
                                .then(response => {
                                    this.message = 'The book was updated successfully!';
                                    if (!this.currentComment.id) {
                                        this.bookComments.push(response.data);
                                    }
                                    this.resetCommentForm(formName);
                                })
                                .catch(e => {
                                    console.log(e);
                                });
                    } else {
                        console.log('Book comment form is not valid!');
                        return false;
                    }
                });
            },
            resetCommentForm(formName) {
                this.$refs[formName].resetFields();
                this.currentComment = {
                    id: null,
                    bookId: null,
                    entry: "",
                }
            },
            clickEdit(comment) {
                this.currentComment = comment
            }
        },
        mounted() {
            this.initData(this.$route.params.id);
        }
    }
</script>

<style scoped>
  .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: #c2c6cf;
    color: #82858c;
  }

  .comments-block {
    border-radius: 3px;
    transition: .2s;
  }

  .comment-list {
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(104, 165, 255, 0.2);
    min-height: 50px;
    max-height: 500px;
    padding: 0;
    margin: 11px 0 0;
    overflow: auto;
    list-style: none;
  }

  .comment-item {
    height: auto;
    border-radius: 3px;
    margin: 10px;
    padding: 9px 15px;
    background: #e3f4ff;
  }
  .comment-entry {
    color: #0062ff;
    font-size: 17px;
  }
</style>
