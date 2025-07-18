import dennisroot from "../assets/dennisroot.png"
import user from "../assets/user.png"
import SearchBar from "./SearchBar.jsx";
import React from "react";
import DropDown from "./DropDown.jsx";
import { Link } from 'react-router-dom';
import { useNavigate } from "react-router-dom";

export default function NavBar() {


    const [showDropDown, setShowDropDown] = React.useState(false);

    const navigate = useNavigate();


    function dropDown(){
        setShowDropDown(showDropDown => !showDropDown);

    }

    function goHome(){
        navigate("/")
    }



    return (
        <header className="navbar">

            <img
                src={dennisroot}
                alt="home-button-image"
                onClick={goHome}
            />
            <Link className="navbar-brand" to="http://localhost:5173/">DeerootShop</Link>

            <div className={"navbar-middle"}>
                <button>Sheets</button>
                <button>Midi</button>
                <SearchBar/>
            </div>
            <img onClick={dropDown} className={"profile-image"}
                 src={user}
            />
                {showDropDown && <DropDown/>}

        </header>
    )
}