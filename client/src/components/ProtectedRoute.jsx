import {Navigate} from "react-router-dom";
//import {useAuth} from "./AuthContext.jsx";
import axios from "axios";
import {useEffect, useState} from "react";

export default function ProtectedAdminRoute({children}) {

    //const { setUser } = useAuth()

    const token = localStorage.getItem("token");

    const [isAdmin, setIsAdmin] = useState(false);

    useEffect( () => {
        axios.get("http://localhost:8080/auth/admin", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
        .then(res => {

            console.log("Data:", res.data);
            if (res.data) {
                console.log("Data:", res.data);
                console.log("Point 1");
                setIsAdmin(true)
            }
            else{
                console.log("Point 2");
                setIsAdmin(false)
            }
        }).catch(err =>{
            if (err.response && err.response.status === 403) {
                console.log("Point 3");
                setIsAdmin(false)
            }
            else{
                console.error("Error in Protected Route admin check:", err)
            }
        })

    }, [])

    console.log("isAdmin" + isAdmin)
    if (isAdmin){
        return children
    }
    return <Navigate to={"/unauthorized"} replace />
}

