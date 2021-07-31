const { createProxyMiddleware } = require('http-proxy-middleware')

module.exports = function (app) {
    app.use(
        createProxyMiddleware('/api', {
            target: 'http://localhost:8080',
            ws: true,
            secure: false,
            changeOrigin: true,
            pathRewrite: {
                '^/api': '', // URL ^/api -> 공백 변경
            },
            cookieDomainRewrite: 'localhost',
            debug: true,
        }),
    )
}
