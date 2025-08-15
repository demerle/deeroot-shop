import {useNavigate, useSearchParams} from "react-router-dom";
import {useEffect} from "react";
import axios from "axios";

export default function Success(){

    /*
    This entire component is for after a purchase for a product
    is completed and the associated logic that comes with that
     */


    const [params] = useSearchParams()
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    useEffect(() => {
        const sessionId = params.get('session_id');



     //   const purchasedItems = localStorage.getItem("purchased_items");
        //const parsedItems = JSON.parse(purchasedItems);


        /*
            This post call both verifies the session id and updates the users owned items.
         */
        console.log(sessionId);
        axios.get(`${import.meta.env.VITE_API_URL}/checkout/verify`,  {
            params: {session_id: sessionId},
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then(res => {

            if (!res.data) {
                alert("Server Error in updating owned items")
            }
            //localStorage.setItem("purchased_items", "");
            navigate("/profile")

        }).catch(err => {
                if (err.response && err.response.status === 403) {
                    console.log("Auth issue in successful payment")
                }
                else{
                    console.log("Error in successful payment: " + err)
                }
            })
    })
}