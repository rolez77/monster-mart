import {Box, Button, Flex, Heading, HStack, Spinner, Stack} from "@chakra-ui/react";
import {useEffect} from "react";
import {jwtDecode} from "jwt-decode";
import {getUsers} from "../../services/client.js";
import {useUserProfile} from "../../hooks/useUserProfile.js";
// import {fetchUserInfo} from "../../util/getInfo.js"
const UserProfilePage = () => {


    const {user, error, loading} = useUserProfile();
    if (loading) return <Spinner />;
    return(
        <>
            <Flex direction = 'column' minHeight="100vh">

                {/*header*/}
                <Flex
                    as = 'header'
                    align ='center'
                    justify = 'space-between'
                    padding = '1.5rem'
                    bg = '#213547'
                    position = 'sticky'
                    color = 'white'
                    top = '0'
                    zIndex = '100'
                    boxShadow='sm'
                >
                    <Heading>
                        Hello, {user ? user.name : "Guest"}
                    </Heading>
                    <HStack spacing={2}>
                        <Button
                            //onClick={goToProfilePage}
                            colorScheme="blue">
                            Profile
                        </Button>
                        <Button
                            className={'buttons'}
                            colorScheme="blue"
                            //onClick={goToAddCardPage}
                        >
                            Post card
                        </Button>
                        <Button
                            className={'buttons'}
                            //onClick={handleLogout}
                            colorScheme="blue">
                            Logout
                        </Button>

                    </HStack>
                </Flex>
            </Flex>

            {/*main*/}

            <Box flex={1}  bg={'gray.50'} p ={8}>
                Content
            </Box>


        </>
    )
}
export default UserProfilePage;