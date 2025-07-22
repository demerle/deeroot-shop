import {Navigate} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";

export default function ProtectedAdminRoute({children}) {

   // const { user } = useAuth()

    const token = localStorage.getItem("token");

    const [isAdmin, setIsAdmin] = useState(false);

    const [isLoading, setIsLoading] = useState(true);

    useEffect( () => {

        /*

        This UseEffect checks if the user is an admin using the backend auth logic.
        the isLoading state is required because this component was returning unauthorized
        before the useEffect completed its logic.

         */

        axios.get("http://localhost:8080/auth/admin", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
        .then(res => {

            if (res.data) {
                setIsAdmin(true)
            }
            else{
                setIsAdmin(false)
            }
            setIsLoading(false)
        }).catch(err => {
            if (err.response && err.response.status === 403) {
                setIsAdmin(false)
            }
            else{
                console.error("Error in Protected Route admin check:", err)
            }
            setIsLoading(false)
        })

    }, [token])





    if (isLoading){
        return <div></div>
    }

    if (isAdmin){
        return children
    }
    return <Navigate to={"/unauthorized"} replace />


}

