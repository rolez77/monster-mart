import {
    Box,
    Button,
    Flex,
    Heading,
    HStack,
    Spinner,
    Stack,
    Text,
    Image,
    VStack,
    Badge,
    SimpleGrid
} from "@chakra-ui/react";
import {useCallback, useEffect, useState} from "react";
import {jwtDecode} from "jwt-decode";
import {getUsers, uploadUserProfilePicture, userProfilePictureUrl} from "../../services/client.js";
import {useUserProfile} from "../../hooks/useUserProfile.js";
// import {fetchUserInfo} from "../../util/getInfo.js"
import {Header as MyHeader} from "../../hooks/Header.jsx";
import {useDropzone} from "react-dropzone";
import {useCardInfo} from "../../hooks/useCardInfo.js";
const UserProfilePage = () => {


    const {user, error, loading} = useUserProfile();
    const {cards, cardError, cardLoading} = useCardInfo();

    if(loading || cardLoading) {return <Spinner />}


    const cardImgId = (cardId) =>
        `${import.meta.env.VITE_API_BASE_URL}/api/v1/cards/${cardId}/image`;

    const usersCards = cards.filter(card => card.user?.id === user?.id);

    const userImgId = (userId) =>
        `${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${userId}/image`
    if (loading) return <Spinner />;
    return(
        <>
            <Flex direction = 'column' minHeight="100vh">
                {/*header*/}
                <MyHeader />
            {/*main*/}
                {loading ?(
                    <Spinner />
                ):(
                    <Box flex={1}
                         bg={'blue.50'}
                         display='flex'
                         justifyContent='center'
                         alignItems='center'

                    >
                        <VStack spacing={6}>
                            <Box
                                bg="white"
                                p={6}
                                rounded="md"
                                shadow="md"
                                width="300px"
                                textAlign="center"
                            >
                                <Box
                                    borderRadius="full"
                                    overflow="hidden"
                                    boxSize="150px"
                                    border="4px solid"
                                    borderColor="blue.100"
                                >
                                    <Image
                                        src={userImgId(user?.id)}
                                        fallbackSrc="mmfavicon.png"
                                        objectFit="cover"
                                        w={'100%'}
                                        h={'100%'}
                                    />
                                </Box>
                            </Box>
                                <MyDropzone userId={user?.id}/>
                                <Text>{user ? user.name : "Guest"}</Text>
                            <Box w={'100%'}>
                                <Heading>My Cards</Heading>
                            </Box>
                            {usersCards.length === 0 ?(
                                <Text> You have not uploaded any cards</Text>
                            ):(
                                <SimpleGrid columns ={{base:1, md:3, lg:4}} spacing={6}>
                                    {usersCards.map((card) => (
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
                        </VStack>
                    </Box>
                )}
            </Flex>

        </>
    )
}

function MyDropzone({userId}){
    const [droppedFile, setDroppedFile] = useState(null);

    const onDrop = useCallback((acceptedFile) => {
        setDroppedFile(acceptedFile[0]);
    },[]);

    const{getRootProps, getInputProps, isDragActive} = useDropzone({
        onDrop,
        maxFiles:1,
        accept:{'image/*':['.jpg','.jpeg','.png']},
    });

    const handleUpload = () =>{
        if(!droppedFile){return;}

        const formData = new FormData();
        formData.append("file", droppedFile);
        uploadUserProfilePicture(userId, formData)
            .then(response => {
                console.log(response);
                window.location.reload();
            })
            .catch(error => {
                console.log(error);
            })
    }

    return (
        <VStack width = "100%">
            <Box
                {...getRootProps()}
                border="2px dashed"
                borderColor="gray.300"
                p={6}
                rounded="md"
                width="100%"
                cursor="pointer"
                bg={isDragActive ? 'gray.100' : 'gray.50'}
                _hover={{ bg: 'gray.100' }}
            >
            <input {...getInputProps()}/>
                {droppedFile?(
                    <Text fontWeight="bold" color="green.500">{droppedFile.name}</Text>
                ):
                    (
                        <Text color="gray.500" fontSize="sm">
                            {isDragActive ? "Drop picture here..." : "Drag & drop profile pic, or click to select"}
                        </Text>
                    )
                }
            </Box>
            {droppedFile && (
                <Button colorScheme="blue" onClick={handleUpload} width="100%">
                    Save Profile Picture
                </Button>
            )}
        </VStack>
    )
}

export default UserProfilePage;