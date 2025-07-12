import dennisroot from "../assets/dennisroot.png"
import user from "../assets/user.png"
import SearchBar from "./SearchBar.jsx";
import React from "react";
import DropDown from "./DropDown.jsx";


export default function NavBar() {


    const [showDropDown, setShowDropDown] = React.useState(false);




    function dropDown(){
        console.log(showDropDown);
        setShowDropDown(showDropDown => !showDropDown);

    }

    function goHome(){
        window.location.href = "http://localhost:5173/";
    }


    return (
        <header className="navbar">
            <img
                src={dennisroot}
                onClick={goHome}
            />
            <a className="navbar-brand" href="http://localhost:5173/">DeerootShop</a>
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