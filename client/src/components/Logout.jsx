import {useAuth} from "./AuthContext.jsx";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
export default function Logout(){

    const { setUser } = useAuth()
    const navigate = useNavigate();

    useEffect(() => {
        setUser({
            role: "GUEST",
            token: "GUEST",
            email: "GUEST"
        })
        navigate("/")
    }, [])

    return null;

}