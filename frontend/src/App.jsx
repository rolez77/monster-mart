
import './App.css'
import AuthProvider from "./components/context/AuthContext.jsx";
import {createBrowserRouter, Route, RouterProvider, Routes} from "react-router-dom";
import Signup from "./components/signup/Signup.jsx";
import Dashboard from "./components/homepage/Dashboard.jsx";
import UserProfilePage from "./components/profile/UserProfilePage.jsx";
import Login from "./components/login/Login.jsx";

function App() {
    return(
        <>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/home" element={<Dashboard />} />
                <Route path="/profile" element={<UserProfilePage />} />
            </Routes>
        </>
    )
}

export default App
