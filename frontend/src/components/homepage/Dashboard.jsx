import {Badge, Box, Button, Flex, Heading, HStack, SimpleGrid, Spinner, VStack, Text} from "@chakra-ui/react";
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import "./dashboard.css"
import {useEffect, useState} from "react";
import {getCards} from "../../services/client.js";


const Dashboard = () => {

    const {logout} = useAuth()
    const navigate = useNavigate();
    const [cards, setCards] = useState([]);
    const[loading, setLoading] = useState(false);
    const[error, setError] = useState(null);

    useEffect(() => {
        fetchCards();
    },[])

    const fetchCards =  () => {
        setLoading(true);
        getCards()
            .then((res) => {
                setCards(res.data);
            })
            .catch((err) => {
                console.log(err);
                setError("Failed to load cards.");
            })
            .finally(() => {
                setLoading(false);
            })
    }

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
            <HStack spacing={2}>
                <Button
                    onClick={goToProfilePage}
                    colorScheme="blue">
                    Profile
                </Button>
                <Button className={'buttons'} onClick={handleLogout} colorScheme="blue">
                    Logout
                </Button>
                <Button
                    className={'buttons'}
                    colorScheme="blue"
                    onClick={goToAddCardPage}>
                    Post card
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
                                <Text> No image</Text>
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