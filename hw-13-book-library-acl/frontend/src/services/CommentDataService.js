import http from "../http";

class CommentDataService {
    findByBookId(id) {
        return http.get(`/comments/book/${id}`);

    }

    create(data) {
        return http.post("/comments", data);
    }

    update(id, data) {
        return http.put(`/comments/${id}`, data);
    }

    delete(id) {
        return http.delete(`/comments/${id}`);
    }
}

export default new CommentDataService();
