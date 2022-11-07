import { useEffect, useState } from 'react'
import axios from 'axios'

const Home = () => {
    const [user, setUser] = useState({})

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
                setUser(data)
            })
            .catch((error) => {
                console.log(error.response)
            })
    }, [])

    return (
        <div>
            <div>Here is your profile</div>
            <br/>
            <div>{`Name: ${user?.username}`}</div>
            <div>{`User Id: ${user?.userId}`}</div>
            <div>{`Provider: ${user?.provider}`}</div>
            <div>{`Roles: ${user?.roles?.join(' ')}`}</div>
            <div>{`Registered: ${user?.creationTimestamp}`}</div>
        </div>
    )
}

export default Home
