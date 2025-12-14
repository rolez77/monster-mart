
import{getUsers, login as performLogin} from "../../services/client.js";
import {jwtDecode} from "jwt-decode";
import {createContext, useContext, useEffect, useState} from "react";

const AuthContext = createContext({});

const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    const setUserFromToken = () => {
        let token = localStorage.getItem("token");

        if(!token){
            console.log("Token not found");
        }
        try{
            if (token) {
                token = jwtDecode(token);
                setUser({
                    username: token.sub,
                    role: token.scopes
                });
            }
        }catch(e){
            console.log("Invalid token detected");
            localStorage.removeItem("token");
            setUser(null);
        }

    }
    useEffect(() => {
        setUserFromToken();
    }, [])


    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword).then(res => {
                const jwtToken = res.headers["authorization"];
                console.log("FULL LOGIN RESPONSE:", res);
                if (!jwtToken) {
                    reject("Token not found in response headers! Check Backend CORS.");
                    return;
                }
                localStorage.setItem("token", jwtToken);

                const decodedToken = jwtDecode(jwtToken);

                setUser({
                    username: decodedToken.sub,
                    role: decodedToken.scopes
                });
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logout = () => {
        localStorage.removeItem("token");
        setUser(null);
    }

    const isUserAuthenticated = () => {
        const token = localStorage.getItem("token");
        if (!token) {
            return false;
        }

        const {exp: expiration} = jwtDecode(token);
        if (Date.now() > expiration * 1000) {
            logout();
            return false;
        }
        return true;
    }
    return (
        <AuthContext.Provider value={{
            user,
            login,
            logout,
            isUserAuthenticated,
            setUserFromToken
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);
export default AuthProvider;
