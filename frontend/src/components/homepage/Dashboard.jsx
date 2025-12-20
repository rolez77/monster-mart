import {Button} from "@chakra-ui/react";
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import "./dashboard.css"

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

    const goToProfilePage = () =>{
        navigate("/profile");
    }


    return(

       <div>
           <div>
               <Button onClick={handleLogout} colorScheme="blue">
                   Logout
               </Button>
           </div>

            <div>
                <Button colorScheme="blue">
                    Post card
                </Button>
            </div>
           <div className = "cardContainer">
                <h1>
                    pokemon
                </h1>
           </div>
           <div>
               <Button
                   onClick={goToProfilePage}
                   colorScheme="blue">
                   Profile
               </Button>
           </div>
       </div>


    )
}

export default Dashboard;