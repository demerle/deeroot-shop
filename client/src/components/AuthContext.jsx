import {useContext, createContext, useState, useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";


const AuthContext = createContext(null);

export function AuthProvider({ children }) {

    const [user, setUser] = useState({
        role: "GUEST",
        token: "GUEST",
        email: "GUEST"
    });

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            axios.get(`${import.meta.env.VITE_API_URL}/users`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then(res => {
                    if (res.data.roles.some(role => role.name === "ADMIN")) {
                        setUser({
                            role: "ADMIN",
                            token: token,
                            email: res.data.email
                        })
                    }
                    else{
                        setUser({
                            role: "USER",
                            token: token,
                            email: res.data.email
                        })
                    }

                })
                .catch(err => {
                    if (!err.response || err.response.status !== 403) {
                        console.log("Error in startup auth: ", err)
                    }
                    /*
                    else if (err.response.status === 403) {
                        localStorage.setItem("token", "")
                        navigate("/")
                    }

                     */
                })
        }
        else{
            console.log("Empty token in AuthProvider")

            //navigate("/")
        }

    }, [])



    return (
        <AuthContext.Provider value = {{user, setUser}}>
            {children}
        </AuthContext.Provider>
    )
}

export function useAuth(){
    return useContext(AuthContext)
}

