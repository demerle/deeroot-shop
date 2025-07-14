import {Link} from "react-router-dom";
import React from "react";

export default function DropDown() {


    const loggedIn = false
    return (
        <div className="dropdown" id="profileDropdown">
            <Link to="http://localhost:5173/profile">My Profile</Link>
            {!loggedIn && <Link to="http://localhost:5173/login">Log in</Link>}

            {loggedIn && <Link to="http://localhost:5173/#">Log out</Link>}
        </div>
    )
}