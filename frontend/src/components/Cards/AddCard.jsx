import {
    Alert,
    AlertIcon,
    Box,
    Button,
    Flex,
    FormLabel,
    Heading,
    Input,
    Select,
    Stack,
    useToast
} from '@chakra-ui/react';
import {Form, Formik, useField} from "formik";
import * as Yup from 'yup';
import "./addcard.css"
import {useNavigate} from "react-router-dom";
import {saveCards} from "../../services/client.js";


const TextInput = ({label, ...props}) => {
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


const SelectInput = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select className="text-input" {...field} {...props} >
                {props.children}
            </Select>
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const AddCard = () => {
    const navigate = useNavigate();
    const toast = useToast();

    return (
        <Flex background={'gray.50'} minH={'100vh'} align={'center'} justify={'center'}>
            <Stack>
                <Heading>Add Card</Heading>
                <Box>
                    <Formik
                        initialValues={{
                            name: "",
                            set: "",
                            price: "",
                            condition: ""
                        }}
                        validationSchema={Yup.object({
                            name: Yup.string().required("Card name is required"),
                            set: Yup.string().required("Card set is required"),
                            price: Yup.string().required("Card set is required"),
                            condition: Yup.string().required("Card set is required"),
                        })}
                        onSubmit={(values, { setSubmitting }) => {
                            setSubmitting(true);
                            saveCards(values)
                                .then(() => {
                                    toast({
                                        title: "Card Added",
                                        status: "success",
                                        duration: 5000,
                                        isClosable: true
                                    });
                                    navigate("/home");
                                })
                                .catch((err) => {
                                    console.log(err);
                                    toast({
                                        title: "Error",
                                        description: err.response?.data?.message || "Something went wrong",
                                        status: "error",
                                        duration: 5000,
                                        isClosable: true
                                    });
                                })
                            .finally(() => {
                                setSubmitting(false);
                            })
                        }}
                    >
                        {({isValid, isSubmitting}) => (
                            <Form>
                                <Stack spacing={2}>
                                    <TextInput
                                        label = "Card Name"
                                        name = "name"
                                        type = "text"
                                        placeholder = "Enter card name"
                                    />
                                    <TextInput
                                        label = "Set"
                                        name = "set"
                                        type = "text"
                                        placeholder = "Enter set"
                                    />
                                    <TextInput
                                        label = "Price"
                                        name = "price"
                                        type = "number"
                                        placeholder = "Enter price"
                                    />
                                    <SelectInput label="Condition" name = "condition">
                                        <option value="" disabled>Select a condition</option>
                                        <option value="MINT">Mint</option>
                                        <option value="GOOD">Good</option>
                                        <option value="SLIGHT">Slightly Damaged</option>
                                        <option value="DAMAGE">Damaged</option>
                                    </SelectInput>
                                    <Button
                                        loadingText="Adding card.."
                                        size="lg"
                                        bg={'blue.400'}
                                        color={'white'}
                                        _hover={{ bg: 'blue.500' }}
                                        isDisabled={!isValid || isSubmitting}
                                        type="submit"
                                        mt={4}
                                        >
                                        Add Card
                                    </Button>
                                </Stack>
                            </Form>
                        )}

                    </Formik>
                </Box>
            </Stack>
        </Flex>

    )
}
export default AddCard;