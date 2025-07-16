import {Link} from "react-router-dom";
import React from "react";
import {useAuth} from "./AuthContext.jsx";

export default function DropDown() {

    const { user } = useAuth()

    const loggedIn = user.role !== "GUEST"

    return (
        <div className="dropdown" id="profileDropdown">
            {loggedIn && <Link>{user.email}</Link>}
            {loggedIn && <hr/> }
            {loggedIn && <Link to="http://localhost:5173/profile">Your Items</Link>}
            {!loggedIn && <Link to="http://localhost:5173/login">Log in</Link>}
            {loggedIn && <Link to="http://localhost:5173/logout">Log out</Link>}
        </div>
    )
}