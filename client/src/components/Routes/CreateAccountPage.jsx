import React, {useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

export default function CreateAccountPage() {


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [retypePassword, setRetypePassword] = useState("");
    const [badEmail, setBadEmail] = useState(false);

    function onSubmit(){

        const json = { email, password, retypePassword};

        if (!email.includes("@") || !email.includes(".")) {
            alert("Please enter a valid email");
            return
        }

        if (password.length < 8){
            alert("Password must be a minimum of 8 characters");
        }

        if (password !== retypePassword) {
            alert("Passwords do not match");
            return
        }
        axios.post('http://localhost:8080/users', json)
            .then(res => {
                console.log("Data:", res.data);
            })
            .catch(err => {
                if (err.response.status === 409) {
                    setBadEmail(true)
                }
                console.error("Error:", err)
            })
    }


    return (
        <div className={"login-container"}>
            <form id="form" className="login-form" onSubmit={e => e.preventDefault()}>
                <label>Email:</label>
                <input
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    type={"email"}
                />

                <label>Password:</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <label>Retype Password:</label>
                <input
                    type="password"
                    value={retypePassword}
                    onChange={(e) => setRetypePassword(e.target.value)}
                />

                <button  className ="button" onClick={onSubmit}>Create Account</button>

                {badEmail &&
                    <h3 style={{color: "red", display: "flex", margin: "5px", backgroundColor: "darkorange"}}>User with
                        email {email} already exists. Please try a different email.</h3>}

                <Link to="http://localhost:5173/login">Already Have An Account? Log In Here</Link>
            </form>
        </div>
    )
}