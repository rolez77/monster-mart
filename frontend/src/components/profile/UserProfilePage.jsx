import {Box, Flex, Heading, Stack} from "@chakra-ui/react";

const UserProfilePage = () => {
    return(
        <>
            <Flex>
                <Heading>
                    Profile Page
                </Heading>

                <Box flex={1}  bg={'gray.50'} p ={8}>
                    Content
                </Box>

            </Flex>

        </>
    )
}
export default UserProfilePage;