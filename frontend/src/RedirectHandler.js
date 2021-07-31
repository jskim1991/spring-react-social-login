import { useEffect } from 'react'
import { useHistory } from 'react-router-dom'

const RedirectHandler = () => {
    const history = useHistory()

    const getCookieValueByKey = (key) => {
        const value = `; ${document.cookie}`
        const parts = value.split(`; ${key}=`)
        if (parts.length === 2) return parts.pop().split(';').shift()
    }

    useEffect(() => {
        const tokenCookie = getCookieValueByKey('token')
        localStorage.setItem('token', tokenCookie)

        history.push('/home')
    }, [])

    return (
        <div>
            <div>Loading...</div>
        </div>
    )
}

export default RedirectHandler
