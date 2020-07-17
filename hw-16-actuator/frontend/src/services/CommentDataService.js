import http from "../http";

class CommentDataService {
    findByBookId(id) {
        return http.get(`/comments/book/${id}`);

    }

    create(data) {
        return http.post("/comments", data);
    }

    update(data) {
        return http.put(`/comments/`, data);
    }

    delete(data) {
        return http.delete(`/comments/`, {data: data});
    }
}

export default new CommentDataService();
