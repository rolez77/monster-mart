import {useNavigate} from "react-router-dom";
import {useAuth} from "../components/context/AuthContext.jsx";
import {Button, Flex, Heading, HStack} from "@chakra-ui/react";
import {useUserProfile} from "./useUserProfile.js";

export const Header = () => {

    const {logout} = useAuth();
    const navigate = useNavigate();
    const{user,loading} = useUserProfile();

    const handleLogout = async () => {
        try{
            await logout();
        }catch(e){
            console.log(e);
        }finally{
            navigate("/");
        }
    }
    const goToHome = () => {
        navigate("/");
    }
    const goToProfilePage = () =>{
        navigate("/profile");
    }

    const goToAddCardPage = () =>{
        navigate("/addCard");
    }

    return(
        <Flex as="header"
              align="center"
              justify="space-between"
              padding="1.5rem"
              bg="#213547"
              color="white"
              position="sticky"
              top="0"
              zIndex="100"
              boxShadow="sm" >
            <h1
                onClick={goToHome}
                style={{cursor: "pointer"}}
            >

                MonsterMart

            </h1>

            <Heading>

                Hello, {loading? "..." :(user ? user.name: "dih")}
            </Heading>
            <HStack spacing={2}>
                <Button
                    onClick={goToProfilePage}
                    colorScheme="blue">
                    Profile
                </Button>
                <Button
                    className={'buttons'}
                    colorScheme="blue"
                    onClick={goToAddCardPage}>
                    Post card
                </Button>
                <Button className={'buttons'} onClick={handleLogout} colorScheme="blue">
                    Logout
                </Button>

            </HStack>
        </Flex>
    )


}