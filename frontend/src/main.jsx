import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';
import AuthProvider from "./components/context/AuthContext.jsx"; 
import { ChakraProvider, extendTheme } from "@chakra-ui/react";
import {BrowserRouter} from "react-router-dom";

import './App.css'

const theme = extendTheme({
    fonts:{
        heading: '"Jersey 20", sans-serif',
        body: '"Jersey 20", sans-serif',
    }
})

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <ChakraProvider theme={theme}>
            <BrowserRouter>
                <AuthProvider>
                    <App/>
                </AuthProvider>
            </BrowserRouter>
        </ChakraProvider>
    </StrictMode>
)
