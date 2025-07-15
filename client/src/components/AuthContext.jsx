import {useContext, createContext, useState} from "react";


const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState({
        role: "GUEST",
        token: "GUEST",
        email: "GUEST"
    });

    return (
        <AuthContext.Provider value = {{user, setUser}}>
            {children}
        </AuthContext.Provider>
    )
}

export function useAuth(){
    return useContext(AuthContext)
}

