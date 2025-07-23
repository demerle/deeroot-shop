import dennisroot from "../assets/dennisroot.png"
import user from "../assets/user.png"
import cart from "../assets/cart.png"
import SearchBar from "./SearchBar.jsx";
import React from "react";
import DropDown from "./DropDown.jsx";
import { Link } from 'react-router-dom';
import { useNavigate } from "react-router-dom";
import Fuse from "fuse.js";
export default function NavBar(props) {


    const [showDropDown, setShowDropDown] = React.useState(false);
    const [midiClicked, setMidiClicked] = React.useState(false);
    const [sheetsClicked, setSheetsClicked] = React.useState(false);


    const navigate = useNavigate();


    function dropDown(){
        setShowDropDown(showDropDown => !showDropDown);
    }

    function goHome(){
        navigate("/")
    }

    function goToCart(){
        navigate("/cart")
    }



    function filterBySheets(){

        if (!sheetsClicked){
            if (midiClicked){
                setMidiClicked(false);
            }

            const newArr = []
            for (let item of props.musicItems){
                if (item.fileType === "application/pdf"){
                    newArr.push(item)
                }
            }
            props.setDisplayItems(newArr)
        }
        else{
            if (!midiClicked){
                props.setDisplayItems(props.musicItems)
            }

        }
        setSheetsClicked(!sheetsClicked)

    }

    function filterByMidi(){

        if (!midiClicked){
            if (sheetsClicked){
                setSheetsClicked(false);
            }

            const newArr = []
            for (let item of props.musicItems){
                if (item.fileType === "audio/midi"){
                    newArr.push(item)
                }
            }
            props.setDisplayItems(newArr)
        }
        else{
            if (!sheetsClicked){
                props.setDisplayItems(props.musicItems)
            }

        }
        setMidiClicked(!midiClicked)
    }


    return (
        <header className="navbar">

            <img
                src={dennisroot}
                alt="home-button-image"
                onClick={goHome}
            />
            <Link className="navbar-brand" to="/">DeerootShop</Link>

            <div className={"navbar-middle"}>
                <button onClick={filterBySheets} style={sheetsClicked ? {color : "white"} : {color: "purple"}}>Sheets</button>
                <button onClick={filterByMidi} style={midiClicked ? {color : "white"} : {color: "purple"}}>Midi</button>
                <SearchBar search={props.search}/>

            </div>
            <img onClick={goToCart} src={cart} className="profile-image"/>
            <img onClick={dropDown} className={"profile-image"}
                 src={user}
            />
            {showDropDown && <DropDown/>}

        </header>
    )
}