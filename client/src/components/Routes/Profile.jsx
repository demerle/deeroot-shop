import MusicItem from "../MusicItem.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {download} from "../../utils/utils.js";
import midi from "../../assets/midi-modified.PNG"

export default function Profile() {

    const [musicItems, setMusicItems] = useState([]);

    const navigate = useNavigate()

    useEffect(() => {

        /*
        This useEffect calls the UserController in the backend to
        obtain a users owned products
         */

        const token = localStorage.getItem("token");
        axios.get('http://localhost:8080/users/owned-items', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(res => {
                setMusicItems(res.data);
            })
            .catch(err => {
                if (err.response && err.response.status === 403) {
                    navigate('/login');
                }
                else{
                    console.error("Error:", err);
                }
            });
    }, []);


    const ownedSheets = musicItems.map((musicItem) => (
        <div key = {musicItem.id}>
            <MusicItem
                item = {musicItem}
                img = {musicItem.fileType === "application/pdf" ? musicItem.s3PreviewUrl : {midi}}
                title= {musicItem.title}
            />
            <button className="button" onClick={() => download(musicItem.fileName)}>Download</button>
        </div>
    ))

    return (
        <>
            { /*<NavBar/> */}
            <br/>
            <div className="solo-text-container"><h1 className="solo-text">Your Items</h1></div>
            <div className="product-container">
                {ownedSheets}
            </div>
        </>
    )
}