import axios from 'axios';

const getAuthConfig = () =>({
    headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
    }
})

export const getUsers = async () =>{

    try{
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users`, getAuthConfig());
    } catch(error){
        throw error;
    }
}

export const getCards = async () =>{
    try{
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/cards`, getAuthConfig());
    }catch(error){
        throw error;
    }
}

export const saveCards = async (card) =>{
    try{
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/cards`,card, getAuthConfig());
    }catch(error){
        throw error;
    }
}

export const saveUser = async(user) =>{
    try{
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users`, user);
    }
    catch(error){
        throw error;
    }
}

export const updateUser = async(id, update) =>{
    try{
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}`, update)
    }catch(error){
        throw error;
    }
}

export const deleteUser = async(id) =>{
    try{
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}`, getAuthConfig());
    }catch(error){
        throw error;
    }
}

export const login = async(userNameAndPassword) =>{
    try{
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`, userNameAndPassword
        );
    }
    catch(error){
        throw error;
    }
}

export const uploadUserProfilePicture = async(id, data) =>{
    try{
        return axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}/profile-image`,
            data,{
                ...getAuthConfig(),
                'Content-Type': 'multipart/form-data',
            }
        );
    }catch(error){
        throw error;
    }
}

export const userProfilePictureUrl = (id) =>
    `${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}/profile-image`;
