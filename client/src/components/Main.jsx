import { useState, useEffect } from 'react'
import Welcome from "./Welcome.jsx";
import MusicItem from "./MusicItem.jsx";
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


    return (
        <main>
            <Welcome />

            <div className="product-list">
                <MusicItem title={"Title1"} price = {"$0.00"} />
                <MusicItem title={"Title2"} price = {"$0.00"} />
            </div>
        </main>
    )
}
