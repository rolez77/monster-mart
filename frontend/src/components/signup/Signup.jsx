import {useAuth} from "../context/AuthContext.jsx";
import {useEffect} from "react";

const Signup = () => {
    const{user, setUserFromToken} = useAuth();
    //TODO implement nav

    useEffect(()=>{
        if(user){
            console.log("Directing to home..")
        }
    });
}


export default Signup;