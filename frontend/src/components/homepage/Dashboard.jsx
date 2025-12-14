import {Button} from "@chakra-ui/react";
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";

const Dashboard = () => {
    const {logout} = useAuth()
    const navigate = useNavigate();

    const handleLogout = async () => {
        try{
            await logout();
        }catch(e){
            console.log(e);
        }finally{
            navigate("/");
        }
    }


    return(

       <div>
           <h1>
               Hello !
           </h1>
           <Button onClick={handleLogout} colorScheme="blue">
               Logout
           </Button>
       </div>


    )
}

export default Dashboard;