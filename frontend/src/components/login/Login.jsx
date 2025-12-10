import {
    Alert,
    AlertIcon,
    Box,
    Button,
    Flex,
    FormLabel,
    Heading,
    Image,
    Input,
    Link,
    Stack,
    Text,
} from '@chakra-ui/react';
import {useAuth} from "../context/AuthContext.jsx";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Formik, Form, useField} from "formik";
import * as Yup from 'yup';

const TextInput = ({label, ...props}) =>{
    const[field,meta] = useField(props);
    return(
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
    )
}
const LoginForm = () => {
    const{login} = useAuth();
    const navigate = useNavigate();
    return(
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    email: Yup.string()
                        .email("Enter a valid email address")
                        .required("Email is required"),
                    password: Yup.string()
                        .max(20, "Password cannot be longer than 20 characters")
                        .min(4, "Password must be longer than 4 characters")
                        .required("Password is required"),
                })
            }
            initialValues={{username: "", password: ""}}
            onSubmit = {(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(result =>{
                    navigate("/home");
                    console.log("logged");
                }).catch(err =>{
                    console.log(err);
                }).finally(() => setSubmitting(false));
            }}>
            {({isValid, isSubmitting}) => (
                <Form>
                    <Stack mt={15} spacing={15}>
                        <TextInput
                            label={"Email"}
                            name = {"username"}
                            type = {"email"}
                            placeholder = {"Type your email"}
                        />
                        <TextInput
                            label={"Password"}
                            name={"password"}
                            type={"password"}
                            placeholder={"Type your password"}
                        />

                        <Button
                            type={"submit"}
                            disabled={!isValid || isSubmitting}>
                            Login
                        </Button>
                    </Stack>
                </Form>
            )}
        </Formik>
    )
}
const Login = () => {
    const{user} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            navigate("/home");
        }
    })

    return(
        <div>
        <Stack  minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                    />
                    <Heading> Welcome to MonsterMart, Coming Soon</Heading>
                    <Heading fontSize={'2xl'} mb={15}>Sign in to your account</Heading>
                    <LoginForm/>
                    <Link color={"blue.500"} href={"/signup"}>
                        Dont have an account? Signup now.
                    </Link>
                </Stack>
            </Flex>
        </Stack>
        </div>
    )
}
export default Login;