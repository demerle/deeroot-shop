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

        /*
        The axios get call is verifying the session ID given by the Stripe API

        The put call is updating the users Owned items/products after
        confirming a legitimate payment sessionID.

         */
        axios.get(`/checkout/verify/${sessionId}`, {
            params: {session_id: sessionId},
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then(res => {
            const purchasedItems = localStorage.getItem("purchased_items");
            const parsedItems = JSON.parse(purchasedItems);

            if (purchasedItems) {
                axios.put("http://localhost:8080/users/owned-items", parsedItems, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }).then(res => {
                    localStorage.setItem("purchased_items", "");
                    navigate("/profile")

                }).catch(err => {
                    if (err.response.status === 403) {
                        console.log("Auth issue in updating users owned items")
                    }
                    else{
                        console.log("Error in updating users owned items: " + err)
                    }
                })
            }
            else{
                console.log("No purchased items in success page?")
            }
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