import {Badge, Box, Button, Flex, Heading, HStack, SimpleGrid, Spinner, VStack, Text, Image} from "@chakra-ui/react";
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import "./dashboard.css"
import {useEffect, useState} from "react";
import {getCards, getUsers} from "../../services/client.js";
import{jwtDecode}from "jwt-decode";
import {useUserProfile} from "../../hooks/useUserProfile.js";
import {useCardInfo} from "../../hooks/useCardInfo.js";
import {Header as MyHeader} from "../../hooks/Header.jsx";


const Dashboard = () => {

    const {logout} = useAuth()
    const navigate = useNavigate();

    const {user, error, loading} = useUserProfile();
    const {cards, cardError, cardLoading} = useCardInfo();



    const cardImgId = (cardId) =>
        `${import.meta.env.VITE_API_BASE_URL}/api/v1/cards/${cardId}/image`;

    return(
        <>

        <Flex direction="column" minHeight="100vh">
            {/*Header*/}
            <MyHeader />
        {/*Main*/}
        <Box flex='1' p={8} bg={'blue.50'}>

            {loading && (
                <Flex>
                    <Spinner size={'lg'} color={'blue'} />
                </Flex>
            )}

            {error && (
                <Flex>
                    <Text>{error}</Text>
                </Flex>
            )}
            {!loading && !error && cards.length === 0 &&(
                <Flex direction ='column' justify='center' align='center'>
                    <Text mb={4} fontSize={'2em'}> No cards to be seen</Text>
                </Flex>
            )}
            {!loading && cards.length > 0 && (
                <SimpleGrid columns ={{base:1, md:3, lg:4}} spacing={6}>
                    {cards.map((card) => (
                        <Box
                            key={card.id}
                            bg={'white'}
                            p={5}
                            shadow = 'md'
                            borderWidth = '1px'
                            borderRadius='lg'
                            _hover={{ shadow: 'xl', transform: 'translateY(-2px)', transition: '0.2s' }}
                        >
                            <Box h={'150px'} bg='gray.100' mb={4} borderRadius={'md'} display={'flex'} justifyContent={'center'} alignItems={'center'}>
                                {card.imageId ? (
                                    <Image
                                        src={cardImgId(card.id)}
                                        alt = {card.name}
                                        objectFit={'contain'}
                                        w={'100%'}
                                        h={'100%'}

                                    />
                                ):(
                                    <Text color={'gray.500'}>No image</Text>
                                )}
                            </Box>

                            <VStack align = "start" spacing={2}>
                                <Badge>
                                    {card.condition}
                                </Badge>
                                <Heading size= 'md' isTruncated w ='100%'>
                                    {card.name}
                                </Heading>
                                <Text fontSize='sm' color='gray.500'>
                                    {card.set}
                                </Text>
                                <Text fontWeight='bold' fontSize='lg' color='blue.600'>
                                    ${card.price}
                                </Text>
                            </VStack>
                        </Box>
                    ))}
                </SimpleGrid>
            )}

        </Box>
        </Flex>
        </>
    )
}

export default Dashboard;