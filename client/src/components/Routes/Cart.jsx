import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import MusicItem from "../MusicItem.jsx";
//import {useAuth} from "../AuthContext.jsx";
import {convertPrice} from "../../utils/utils.js";

export default function Cart() {


    const token = localStorage.getItem("token");
    const [cartItems, setCartItems] = useState([]);
    const navigate = useNavigate();
    //const {user} = useAuth()


    /*
        Calling backend API to obtain all of the items in the cart
        on first render
    */
    useEffect(() => {

        axios.get(`${import.meta.env.VITE_API_URL}/users/cart`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {
            setCartItems(res.data);

        }).catch(err => {
            if (err.response && err.response.status === 403) {
                navigate("/login")
            } else if (err.response && err.response.status === 404) {
                alert("Sheet was not found and cannot be purchased as of now.")
            }
            console.error("Error:", err);
        })
    }, []);


    function buyAll() {

        let amount = 0
        let name = ""
        for (let i = 0; i < cartItems.length; i++) {
            amount += cartItems[i].price;
            name += cartItems[i].title + "\n"
        }


        const dto = {
            quantity: 1,
            name: name,
            currency: "USD",
            items: cartItems
        }
        axios.post(`${import.meta.env.VITE_API_URL}/checkout`, dto, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {
            //localStorage.setItem("purchased_items", JSON.stringify(cartItems))
            window.location.href = res.data.sessionUrl

        }).catch(err => {
            console.error("Error:", err);
        })
    }


    function removeFromCart(item) {
        axios.delete(`${import.meta.env.VITE_API_URL}/users/cart/` + item.id, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {
            setCartItems(res.data);
        }).catch(err => {
            if (err.response && err.response.status === 403) {
                console.log("Auth Error in deletion from cart: " + err)
            } else {
                console.error("Error:", err);
            }
        })
    }

    let sum = 0

    const items = cartItems.map((item) => {
        sum += item.price;
        return (
            <div key={item.id}>
                <MusicItem
                    item={item}
                    description={item.description}
                    price={item.price}
                    title={item.title}
                    img={item.s3PreviewUrl}
                />
                <button className="button" onClick={() => removeFromCart(item)}>Remove from Cart</button>
            </div>
        )
    })

    return (
        <>


            <div className={'product-container'}>
                {items}

            </div>
            {cartItems.length > 0 ?
                <>
                    <br/><br/>
                    <div style={{
                        display: "flex",
                        flexDirection: "row",
                        alignItems: "center"
                    }}>
                        <button className="button" onClick={buyAll}>Buy All</button>
                        <h3>Total Price: {convertPrice(sum)}</h3>
                    </div>
                </>
                :
                <div className="solo-text-container"><h4 className="solo-text">Cart is Empty</h4></div>
            }
        </>
    )
}
