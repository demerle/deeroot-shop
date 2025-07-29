import {Link} from "react-router-dom";
import React from "react";
import {useAuth} from "./AuthContext.jsx";

export default function DropDown() {

    const { user } = useAuth()

    const loggedIn = user.role !== "GUEST"

    console.log(user.role)


    return (
        <div className="dropdown" id="profileDropdown">
            {loggedIn && <Link id="email-link">{user.email}</Link>}
            {loggedIn && <hr/> }
            {loggedIn && <Link to="/profile">Your Items</Link>}
            {!loggedIn && <Link to="/login">Log in</Link>}
            {loggedIn && <Link to="/logout">Log out</Link>}
            {user.role === "ADMIN" && <Link to="/admin">{user.role}</Link>}
        </div>
    )
}