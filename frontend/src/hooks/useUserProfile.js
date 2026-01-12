import {useEffect, useState} from "react";
import {jwtDecode} from "jwt-decode";
import {getUsers} from "../services/client.js";

export const useUserProfile = () => {
    const [user, setUser] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchUserData = async () => {
            setLoading(true);
            try{
                const token = localStorage.getItem("token");
                if(!token){
                    console.log("Token not found");
                    setLoading(false);
                    return;
                }
                const decoded = jwtDecode(token);
                const myEmail = decoded.sub;
                const res = await getUsers();
                const myUser = res.data.find(u=>u.username===myEmail);
                if(myUser){
                    setUser(myUser);
                }else{
                    setError("User not found");
                }
            }catch(err){
                console.log(err);
            }finally {
                setLoading(false);
            }
        };
        fetchUserData();
    },[]);
    return {user, error, loading};
}