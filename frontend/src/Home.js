import { useEffect, useState } from 'react'
import axios from 'axios'

const Home = () => {
    const [name, setName] = useState('')
    const [provider, setProvider] = useState('')

    useEffect(() => {
        async function getUserName() {
            return await axios.get('/api/user', {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            })
        }

        getUserName()
            .then((response) => {
                const { data } = response
                console.log(data)
                setName(data.user.username)
                setProvider(data.user.provider)
            })
            .catch((error) => {
                console.log(error.response)
            })
    }, [])

    return (
        <div>
            <div>Hello there</div>
            <div>{`OAuth2.0 Client: ${provider}`}</div>
            <div>{`Name: ${name}`}</div>
        </div>
    )
}

export default Home
