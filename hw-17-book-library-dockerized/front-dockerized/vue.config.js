module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: {
            '/api': {
                target: 'http://localhost:9090',
                pathRewrite: {'^/api' : ''},
                ws: true,
                changeOrigin: true,
            }
        }
    }
};
