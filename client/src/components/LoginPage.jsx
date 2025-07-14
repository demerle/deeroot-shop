import React, {useState} from 'react';
import axios from "axios";
import {Link} from "react-router-dom";
export default function LoginPage() {


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [badLogin, setBadLogin] = useState(false)

    function onSubmit(){

        const json = { email, password };
        axios.post('http://localhost:8080/auth', json)
            .then(res => {
                console.log("Data:", res.data);
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


    return (
        <>
            <form id="form" className="login-form" onSubmit={e => e.preventDefault()}>
                <label>Email:</label>
                <input value = {email} onChange={(e) => setEmail(e.target.value)} />

                <label>Password:</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </form>

            <button onClick={onSubmit}>Log In</button>
            {badLogin && <h3 style = {{color : "red", display : "flex", margin: "5px", backgroundColor : "darkorange"}}>Login Failed, Bad Credentials</h3>}
            <Link to="http://localhost:5173/create-account">Not Logged In? Create an Account Here</Link>
        </>
    )
}