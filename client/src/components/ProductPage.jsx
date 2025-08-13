import axios from "axios";
import {useNavigate} from "react-router-dom";
import blurredSheet from "../assets/blurredSheetPage.jpg";
import {convertPrice} from "../utils/utils.js"
import {useEffect, useState} from "react";
import {download} from "../utils/utils.js";
import midi from "../assets/midi-modified.PNG"

export default function ProductPage(props) {


    const token = localStorage.getItem("token");
    const item = props.musicItem
    const navigate = useNavigate();

    const [owned, setOwned] = useState(false);



    useEffect(() => {

        axios.get(`${import.meta.env.VITE_API_URL || ''}/users/owned-items/` + item.id, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {
            setOwned(res.data)
        }).catch(err => {
            console.error("Error:", err);
        })
    },[]);




    function addToCart(id){
        axios.post(`${import.meta.env.VITE_API_URL}/users/cart/` + id,{},  {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then(() =>{
            alert("Item Added to Cart")
            navigate("/cart")
        }).catch(err => {
            if (err.response && err.response.status === 403) {
                navigate("/login")
            }
            else if (err.response && err.response.status === 404) {
                alert("Sheet was not found and cannot be purchased as of now.")
            }
            console.error("Error:", err);
        })
    }

    function buyItNow(item){
        const dto = {
            amount: item.price * 100,
            quantity: 1,
            name: item.title,
            currency: "USD",
            items: [item]
        }
        axios.post(`${import.meta.env.VITE_API_URL}/checkout`, dto,  {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {
            // localStorage.setItem("purchased_items", JSON.stringify([item]))
            if (!res.data){
                navigate("/profile")
            }
            if (res.data.sessionUrl){
                window.location.href = res.data.sessionUrl
            }
            else{
                alert("No sessionUrl found for payment.")
            }

        }).catch(err => {
            console.error("Error:", err);
        })
    }
    let image = null
    if (item.fileType === "application/pdf"){
        image = item.s3PreviewUrl === "" ? blurredSheet : item.s3PreviewUrl
    }
    else{
        image = midi
    }

    return (
        <div className = "product-page">
            <img className = "product-image" src={image} alt="Picture of Sheet"/>
            <div className="product-details">
                <div>
                    <h1>{item.title}</h1>
                    {item.fileType === "application/pdf" && <p>Total Pages: {item.numPages}</p>}
                    <p>{item.description}</p>
                    <p>{convertPrice(item.price)}</p>
                </div>

                <div className="button-container">
                    {owned ?
                        <>
                            <p>You Already Own This</p>
                            <button onClick={() => download(item.fileName)}>Download</button>
                        </>
                        :
                        <>
                            {
                            item.price !== 0 ?
                                <>
                                    <button onClick={() => addToCart(item.id)}>Add to Cart</button>
                                    <button onClick={() => buyItNow(item)}>Buy It Now</button>
                                </>
                                :
                                <button onClick={() => buyItNow(item)}>Get</button>
                        }
                        </>
                    }
                </div>
            </div>
        </div>
    )
}