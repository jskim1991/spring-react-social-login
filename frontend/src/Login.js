const Login = () => {
    const naverLogin = () => {
        window.location.assign('/api/oauth2/authorization/naver')
    }

    const kakaoLogin = () => {
        window.location.assign('/api/oauth2/authorization/kakao')
    }

    const googleLogin = () => {
        window.location.assign('/api/oauth2/authorization/google')
    }

    return (
        <div>
            <div>Login</div>
            <button onClick={naverLogin}>Naver Login</button>
            <button onClick={kakaoLogin}>Kakao Login</button>
            <button onClick={googleLogin}>Google Login</button>
        </div>
    )
}

export default Login
