import MusicItem from "../MusicItem.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import {useAuth} from "../AuthContext.jsx";
import {useNavigate} from "react-router-dom";

export default function Profile() {

    const {user, setUser} = useAuth()

    const [musicItems, setMusicItems] = useState([]);

    const navigate = useNavigate()

    useEffect(() => {

        /*
        This useEffect calls the UserController in the backend to
        obtain a users owned products
         */
        axios.get('http://localhost:8080/users/owned-items', {
            headers: {
                Authorization: `Bearer ${user.token}`
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



    function download(fileName){
        axios.get('http://localhost:8080/music-items/download/' + fileName, {
            headers: {
                Authorization: `Bearer ${user.token}`,
            }
        })
            .then(res => {
                console.log("res.data : " + res.data);
            })
            .catch(err => {
                if (err.response && err.response.status === 403) {
                    (console.log("403 when download attempted"))
                }
                if (err.response && err.response.status === 404) {
                    alert("No such file was found for download")
                }
                else{
                    console.error("Error:", err);
                }
            });
    }


    const ownedSheets = musicItems.map((musicItem) => (
        <div key = {musicItem.id}>
            <MusicItem
                item = {musicItem}

                title={musicItem.title}
            />
            <button onClick={() => download(musicItem.fileName)}>Download</button>
        </div>
    ))

    return (
        <>
            { /*<NavBar/> */}
            <h1>Your Items</h1>
            <div className= "product-container">
                {ownedSheets}
            </div>
        </>
    )
}