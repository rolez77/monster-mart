import {useEffect, useState} from "react";
import {getCards} from "../services/client.js";

export const useCardInfo = () => {
    const [cards, setCards] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCardInfo = async () => {
            setLoading(true);
            try{
                const response = await getCards();
                setCards(response.data);
            }
            catch(error){
                console.log(error);
                setError(error);
            }
            finally{
                setLoading(false);
            }
        }
        fetchCardInfo();
    },[])
    return {cards, error, loading};
}