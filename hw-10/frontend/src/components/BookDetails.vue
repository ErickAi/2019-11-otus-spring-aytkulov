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
          <h1 style="padding-bottom: 11px">{{ currentBook.name }}</h1>
          <h3 style="padding-bottom: 11px">{{ currentBook.author.name }}</h3>
          <h4 :key="genre.id"
              :prop="`genres.${index}.name`"
              :v-bind="index"
              style="padding-left: 20px"
              v-for="(genre, index) in currentBook.genres"
          ><span>{{ genre.name }}</span></h4>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div>
            <h1>Comments</h1>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
    import NavMenu from "@/components/NavMenu";
    import BookDataService from "../services/BookDataService";

    export default {
        name: "BookDetails",
        components: {
            NavMenu,
        },
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
                url: 'undefined'
                // url: 'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'
            };
        },
        methods: {
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
        },
        mounted() {
            this.requestBookAndSetData(this.$route.params.id);
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
</style>
