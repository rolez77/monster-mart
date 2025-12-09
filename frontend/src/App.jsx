
import './App.css'
import AuthProvider from "./components/context/AuthContext.jsx";
import {createBrowserRouter} from "react-router-dom";
import Signup from "./components/signup/Signup.jsx";
import Dashboard from "./components/homepage/Dashboard.jsx";
import UserProfilePage from "./components/profile/UserProfilePage.jsx";
import Login from "./components/login/Login.jsx";

function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Login/>
        },
        {
            path: "/signup",
            element: <Signup/>
        },
        {
            path: "/home",
            element: <Dashboard/>
        },
        {
            path: "/profile",
            element: <UserProfilePage/>
        }
    ])
}

export default App
