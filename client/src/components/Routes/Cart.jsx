import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import MusicItem from "../MusicItem.jsx";
//import {useAuth} from "../AuthContext.jsx";

export default function Cart(props) {


    const token = localStorage.getItem("token");
    const [cartItems, setCartItems] = useState([]);
    const navigate = useNavigate();
    //const {user} = useAuth()

    useEffect(() => {

        axios.get('http://localhost:8080/users/cart', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {
            setCartItems(res.data);

        }).catch(err => {
            if (err.response && err.response.status === 403) {
                navigate("/login")
            }
            else if (err.response && err.response.status === 404) {
                alert("Sheet was not found and cannot be purchased as of now.")
            }
            console.error("Error:", err);
        })
    },[]);

    const items = cartItems.map((item) => (
        <MusicItem
            key={item.id}
            item={item}
            description = {item.description}
            price = {item.price}
            title = {item.title}
        />
    ))
    return (
        <>
            {items}
            <button>Buy All</button>
        </>

    )
}