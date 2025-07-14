import { useState, useEffect } from 'react'
import Welcome from "./Welcome.jsx";
import MusicItem from "./MusicItem.jsx";
import axios from "axios"

export default function Main() {

    /*
    const [meme, setMeme] = useState( {
        imgUrl : "http://i.imgflip.com/1bij.jpg",
        topText : "One does not simply",
        bottomText : "Walk into Mordor"
    })


    const [images, setImages] = useState([])

    useEffect( () => {
        fetch("https://api.imgflip.com/get_memes")
            .then(res => res.json())
            .then(data => setImages(data.data.memes))
    }, [])

    function handleClick() {
        let randomNumber = Math.floor(Math.random() * images.length)
        setMeme(prevMeme => {

            return {
                ...prevMeme,
                imgUrl: images[randomNumber].url
            }
        })
    }


    function handleChange(event) {
        const {value, name} = event.currentTarget

        setMeme(prevMeme => {
            return {
                ...prevMeme,
            [name] : value
            }
        })
    }
    */

    /*
    useEffect( () => {
        fetch("https://localhost:8080/music-items")
            .then(res => res.json())
        // API call to fetch all sheets etc
        // To be completed
    }, [])


     */


    const [musicItems, setMusicItems] = useState([])

    useEffect(() => {
        axios.get('http://localhost:8080/music-items')
            .then(res => {
                console.log("Data:", res.data);
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
