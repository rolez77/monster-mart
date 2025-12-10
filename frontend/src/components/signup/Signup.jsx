import {useAuth} from "../context/AuthContext.jsx";
import {useEffect} from "react";
import {Alert, AlertIcon, Box, Flex, FormLabel, Heading, Image, Input, Link, Stack, Text} from "@chakra-ui/react";
import {useField} from "formik";
import {useNavigate} from "react-router-dom";
import CreateUser from "./CreateUser.jsx";

const Signup = () => {

    const MyTextInput = ({label, ...props}) => {
        const [field, meta] = useField(props);
        return (
            <Box>
                <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
                <Input className="text-input" {...field} {...props} />
                {meta.touched && meta.error ? (
                    <Alert className="error" status={"error"} mt={2}>
                        <AlertIcon/>
                        {meta.error}
                    </Alert>
                ) : null}
            </Box>
        );
    };
    const{user, setUserFromToken} = useAuth();
    const navigate = useNavigate();

    useEffect(()=>{
        if(user){
            navigate("/home")
        }
    });

    return(
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={15}>Register for an account</Heading>
                    <CreateUser onSuccess ={(token) =>{
                        localStorage.setItem("token", token)
                        setUserFromToken(token)
                        navigate("/home")
                    }}/>
                    <Link color={"blue.500"} href={"/"}>
                        Have an account? Login now.
                    </Link>
                </Stack>

            </Flex>
        </Stack>
    )
}


export default Signup;