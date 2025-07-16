import {useContext, createContext, useState, useEffect} from "react";
import axios from "axios";


const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState({
        role: "GUEST",
        token: "GUEST",
        email: "GUEST"
    });

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            axios.get("http://localhost:8080/users", {
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
                })
        }
        else{
            console.log("Empty token in AuthProvider")
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

