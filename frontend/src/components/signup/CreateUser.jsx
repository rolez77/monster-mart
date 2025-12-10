import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import{saveUser} from "../../services/client.js";

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


const CreateUser = ({onSuccess}) =>{
    return(
        <>
            <Formik
            initialValues={{
                name: "",
                username: "",
                email: "",
                password: "",
            }}
            validationSchema={Yup.object({
                name: Yup.string()
                    .max(20, "Name cannot be longer than 20 characters long")
                    .required("Name is required"),
                username: Yup.string()
                    .max(20, "Name cannot be longer than 20 characters long")
                    .required("Username is required"),
                email: Yup.string()
                    .max(20, "Email cannot be longer than 20 characters long")
                    .required("Email is required"),
                password: Yup.string()
                    .max(20, "Password cannot be longer than 20 characters long")
                    .min(4, "Password must be longer than 4 characters long")
                    .required("Password is required"),
            })}
            onSubmit={(user, { setSubmitting }) => {
                setSubmitting(true);
                saveUser(user)
                    .then(result => {
                        console.log(result);
                        onSuccess(result.headers["authorization"]);
                    }).catch(error => {
                        console.log(error);
                }).finally(() => {
                    setSubmitting(false);
                })
            }}
            >
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Name"
                            />

                            <MyTextInput
                                label="Username"
                                name="username"
                                type="text"
                                placeholder="Username"
                            />
                            <MyTextInput
                                label="Email Address"
                                name="email"
                                type="email"
                                placeholder="example@example.com"
                            />

                            <MyTextInput
                                label="Password"
                                name="password"
                                type="password"
                                placeholder={"pick a secure password"}
                            />
                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    )
};

export default CreateUser;