import React, { useEffect, useState } from 'react';
import axios from "axios";
import {Link, Navigate, useNavigate} from "react-router-dom";
import {useAuth} from "../AuthContext.jsx";
import {jwtDecode} from "jwt-decode";
export default function LoginPage() {


    const [email, setEmail] = useState("dumb@gmail.com");
    const [password, setPassword] = useState("dumbPassword");
    const [badLogin, setBadLogin] = useState(false)
    const {user, setUser} = useAuth()
    const navigate = useNavigate();

    function onSubmit(){

        const json = { email, password };
        axios.post('http://localhost:8080/auth', json)
            .then(res => {
                const decode = jwtDecode(res.data.token)
                const userEmail = decode.sub

                setUser({
                    role: "USER",
                    token: res.data.token,
                    email: userEmail
                })
                localStorage.setItem("token", res.data.token)

            })
            .catch(err => {
                if (err.response.status === 401) {
                    setBadLogin(true)
                }
                else{
                    console.error("Error:", err)
                }
            })
    }

    useEffect(() => {
        if (!user){
            console.log("User is null in Login useEffect")
        }
        if (user.role !== "GUEST") {
            navigate('/')
        }
    }, [user, navigate])


    return (
        <>
            <form id="form" className="login-form" onSubmit={e => e.preventDefault()}>
                <label>Email:</label>
                <input value={email} onChange={(e) => setEmail(e.target.value)}/>

                <label>Password:</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button onClick={onSubmit}>Log In</button>
            </form>


            {badLogin && <h3 style = {{color : "red", display : "flex", margin: "5px", backgroundColor : "darkorange"}}>Login Failed, Bad Credentials</h3>}
            <Link to="http://localhost:5173/create-account">Not Logged In? Create an Account Here</Link>
        </>
    )
}