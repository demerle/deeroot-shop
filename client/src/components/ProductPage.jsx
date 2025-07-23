import sheet from "../assets/sheet.jpg"
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function ProductPage(props) {


    const token = localStorage.getItem("token");
    const item = props.musicItem
    const navigate = useNavigate();


    function addToCart(id){
        axios.post('http://localhost:8080/users/cart/' + id,{},  {
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
            currency: "USD"
        }
        axios.post('http://localhost:8080/checkout', dto,  {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {
            localStorage.setItem("purchased_items", JSON.stringify([item]))
            window.location.href = res.data.sessionUrl

        }).catch(err => {
            console.error("Error:", err);
        })
    }





    return (
        <>
            <img src = {sheet} alt = "Picture of Sheet"/>
            <div className="product-details">
                <h1>{item.title}</h1>
                <p>{item.description}</p>
                <p>{item.price}</p>
                <button onClick={() => addToCart(item.id)}>Add to Cart</button>
                <button onClick={() => buyItNow(item)}>Buy It Now</button>
                {/*

                // If user Owns Sheet/Midi, Conditionally render the download button instead of buy, and remove the price as well


                */}
            </div>
        </>
    )
}