
import{getUsers, login as performLogin} from "../../services/client.js";
import {jwtDecode} from "jwt-decode";
import {createContext, useContext, useEffect, useState} from "react";

const AuthContext = createContext({});

const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    const setUserFromToken = () => {
        let token = localStorage.getItem("token");
        if (token) {
            token = jwtDecode(token);
            setUser({
                username: token.sub,
                role: token.scopes
            });
        }
    }
    useEffect(() => {
        setUserFromToken();
    }, [])


    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword).then(res => {
                const jwtToken = res.headers["authorization"];
                localStorage.setItem("token", jwtToken);

                const decodedToken = jwtDecode(jwtToken);

                setUser({
                    username: decodedToken.username,
                    role: decodedToken.scopes
                });
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logOut = () => {
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
            logOut();
            return false;
        }
        return true;
    }
    return (
        <AuthContext.Provider value={{
            user,
            login,
            logOut,
            isUserAuthenticated,
            setUserFromToken
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);
export default AuthProvider;
