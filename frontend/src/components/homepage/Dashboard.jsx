import {Badge, Box, Button, Flex, Heading, HStack, SimpleGrid, Spinner, VStack, Text, Image} from "@chakra-ui/react";
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import "./dashboard.css"
import {useEffect, useState} from "react";
import {getCards, getUsers} from "../../services/client.js";
import{jwtDecode}from "jwt-decode";
import {useUserProfile} from "../../hooks/useUserProfile.js";
import {useCardInfo} from "../../hooks/useCardInfo.js";


const Dashboard = () => {

    const {logout} = useAuth()
    const navigate = useNavigate();
    // const [cards, setCards] = useState([]);
    // const[loading, setLoading] = useState(false);
    // const[error, setError] = useState(null);
    // const[user, setUser] = useState(null);

    const {user, error, loading} = useUserProfile();
    const {cards, error2, loading2} = useCardInfo();
    // useEffect(() => {
    //     fetchCards();
    // },[])
    //
    // useEffect(() => {
    //     fetchUserInfo();
    // },[])
    //
    // const fetchUserInfo = () =>{
    //
    //     const token = localStorage.getItem("token");
    //     if(!token){
    //         console.log("token not found");
    //         return;
    //     }
    //
    //     const decoded = jwtDecode(token);
    //     const myEmail = decoded.sub;
    //
    //
    //     getUsers().then(res=>{
    //         console.log(myEmail);
    //         console.log(res.data);
    //         const myUser = res.data.find(u=> u.username === myEmail);
    //         setUser(myUser);
    //
    //         if(!myUser) {
    //             console.log("no user found");
    //         }else{
    //             console.log("User found");
    //         }
    //         setUser(myUser);
    //     }).catch(err=>{
    //         console.log(err);
    //     });
    //
    // }
    //
    // const fetchCards =  () => {
    //
    //     setLoading(true);
    //     getCards()
    //         .then((res) => {
    //             setCards(res.data);
    //         })
    //         .catch((err) => {
    //             console.log(err);
    //             setError("Failed to load cards.");
    //         })
    //         .finally(() => {
    //             setLoading(false);
    //         })
    //
    // }

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

    const goToAddCardPage = () =>{
        navigate("/addCard");
    }

    const cardImgId = (cardId) =>
        `${import.meta.env.VITE_API_BASE_URL}/api/v1/cards/${cardId}/image`;

    return(
        <>

        <Flex direction="column" minHeight="100vh">
            {/*Header*/}
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
            <h1>
                MonsterMart
            </h1>

            <Heading>

                Hello, {user ? user.name: "dih"}
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
        {/*Main*/}
        <Box flex='1' p={8} bg={'gray.50'}>

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