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
            {loggedIn && <Link to="/profile">Your Items</Link>}
            {!loggedIn && <Link to="/login">Log in</Link>}
            {loggedIn && <Link to="/logout">Log out</Link>}
            <Link>{user.role}</Link>
        </div>
    )
}