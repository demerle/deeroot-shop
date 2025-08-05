import {Link} from "react-router-dom";
import React, {useEffect} from "react";
import {useAuth} from "./AuthContext.jsx";
import {jwtDecode} from "jwt-decode";

export default function DropDown() {

    const { user } = useAuth()

    const loggedIn = user.role !== "GUEST"

    const token = localStorage.getItem("token")


    //user.email was not updating properly on login so I pivoted to using the jwt token directly for this logic
    let email = ""
    if (token) {
        try{
            email = jwtDecode(token).sub
        }
        catch{
            email = ""
        }
    }


    return (
        <div className="dropdown" id="profileDropdown">
            {loggedIn && <Link to = "/" id="email-link">{email}</Link>}
            {loggedIn && <hr/> }
            {loggedIn && <Link to="/profile">Your Items</Link>}
            {!loggedIn && <Link to="/login">Log in</Link>}
            {loggedIn && <Link to="/logout">Log out</Link>}
            {user.role === "ADMIN" && <Link to="/admin">{user.role}</Link>}
        </div>
    )


}