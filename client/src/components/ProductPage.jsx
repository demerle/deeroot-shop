import sheet from "../assets/sheet.jpg"
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function ProductPage(props) {


    const token = localStorage.getItem("token");
    const item = props.musicItem
    const navigate = useNavigate();

    console.log(token)

    function addToCart(id){
        axios.post('http://localhost:8080/users/cart/' + id,{},  {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then(() =>{
            alert("Item Added to Cart")
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

    function buyItNow(){
        console.log("buyItNow");
    }




    return (
        <>
            <img src = {sheet} alt = "Picture of Sheet"/>
            <div className="product-details">
                <h1>{item.title}</h1>
                <p>{item.description}</p>
                <p>{item.price}</p>
                <button onClick={() => addToCart(item.id)}>Add to Cart</button>
                <button onClick={() => buyItNow(item.id)}>Buy It Now</button>
                {/*

                // If user Owns Sheet/Midi, Conditionally render the download button instead of buy, and remove the price as well


                */}
            </div>
        </>
    )
}