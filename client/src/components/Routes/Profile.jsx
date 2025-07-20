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
        axios.get('http://localhost:8080/users/owned-items', {
            headers: {
                Authorization: `Bearer ${user.token}`,
            }
        })
            .then(res => {
                setMusicItems(res.data);
                console.log("res.data : " + res.data);
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
            <MusicItem
                item = {musicItem}
                key= {musicItem.id}
                title={musicItem.title}
                price={musicItem.price}
            />
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