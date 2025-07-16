import { useState, useEffect } from 'react'
import Welcome from "./Welcome.jsx";
import MusicItem from "./MusicItem.jsx";
import axios from "axios"

export default function Main() {


    const [musicItems, setMusicItems] = useState([])

    useEffect(() => {
        axios.get('http://localhost:8080/music-items')
            .then(res => {
                setMusicItems(res.data);
            })
            .catch(err => {
                console.error("Error:", err);
            });
    }, []);



    return (
        <main>
            <Welcome/>

            <div className="product-container">

                {musicItems.map((musicItem) => (
                    <MusicItem item = {musicItem} key={musicItem.id} title={musicItem.title} price={musicItem.price}/>
                ))}
            </div>
        </main>
    )
}
