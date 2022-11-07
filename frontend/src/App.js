import { BrowserRouter, Route } from 'react-router-dom'
import Login from './Login'
import Home from './Home'
import RedirectHandler from './RedirectHandler'

function App() {
    return (
        <BrowserRouter>
            <Route exact path="/">
                <Login />
            </Route>
            <Route exact path="/home">
                <Home />
            </Route>
            <Route exact path="/login/callback">
                <RedirectHandler />
            </Route>
        </BrowserRouter>
    )
}

export default App
