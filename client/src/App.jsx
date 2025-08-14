
import React, {useEffect, useState} from "react";
import {BrowserRouter, Routes, Route, useLocation} from "react-router-dom";
import LoginPage from "./components/Routes/LoginPage.jsx";
import Home from "./components/Routes/Home.jsx";
import CreateAccountPage from "./components/Routes/CreateAccountPage.jsx";
import About from "./components/Routes/About.jsx";
import Profile from "./components/Routes/Profile.jsx";
import NavBar from "./components/NavBar.jsx";
import Admin from "./components/Routes/Admin.jsx";
import Unauthorized from "./components/Routes/Unauthorized.jsx";
import {AuthProvider, useAuth} from "./components/AuthContext.jsx";
import Logout from "./components/Routes/Logout.jsx";
import ProtectedAdminRoute from "./components/ProtectedRoute.jsx";
import axios from "axios";
import ProductPage from "./components/ProductPage.jsx";
import Cart from "./components/Routes/Cart.jsx";
import Success from "./components/Routes/Success.jsx";
import Fuse from 'fuse.js';
function AppRoutes() {



    const location = useLocation()
    const hideNavBarRoutes = ['/login', '/create-account']
    const shouldHideNavBar = hideNavBarRoutes.includes(location.pathname);

    const [musicItems, setMusicItems] = useState([])
    const [displayItems, setDisplayItems] = useState([])
    const [showDropDown, setShowDropDown] = React.useState(false);

    const {user} = useAuth()
    useEffect(() => {

        //to undisplay the dropdown on any click
        document.addEventListener("click", e => {
            if (e.target.id !== "dropdown") {
                setShowDropDown(false);
            }
        })

        axios.get(`${import.meta.env.VITE_API_URL}/music-items`)
            .then(res => {
                setMusicItems(res.data)
                setDisplayItems(res.data)
            })
            .catch(err => {
                console.error("Error:", err);
            });
    }, []);

    /*
    if (musicItems.length === 0) {
        return <div>Loading...</div>
    }

     */


   // const {user, setUser} = useAuth()

    const productPages = musicItems.map((musicItem) => (
        <Route
            path = {"product/" + musicItem.id}
            element = {<ProductPage musicItem={musicItem} />}
            key={musicItem.id}
        />
    ))


    function searchBar(inputString){
        let thresh = 0.1
        if (inputString === ""){
            setDisplayItems(musicItems)
            return
        }
        const fuse = new Fuse(musicItems,{
            keys: ["title", 'composer'],
            shouldSort: true,
            threshold: thresh
        })
        const result = fuse.search(inputString)
        setDisplayItems(result.map((item) => item.item))
    }


    return (
    <>

            {!shouldHideNavBar && <NavBar musicItems={musicItems} setDisplayItems={setDisplayItems} search={searchBar} showDropDown = {showDropDown} setShowDropDown = {setShowDropDown}/>}
            <Routes>
                <Route path="/" element={<Home musicItems = {displayItems} actualMusicItems={musicItems}/>}  />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/create-account" element={<CreateAccountPage />} />
                <Route path="/about" element={<About/>} />
                <Route path="/profile" element={<Profile/>} />
                <Route path="/admin" element={
                    <ProtectedAdminRoute>
                        <Admin />
                    </ProtectedAdminRoute>
                }/>
                <Route path ="/unauthorized" element={<Unauthorized />}/>
                <Route path ="/logout" element={<Logout />}/>
                {productPages}
                <Route path = "/cart" element={<Cart />}/>
                <Route path = "/success" element={<Success />}/>

            </Routes>

    </>
    )
}

function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <AppRoutes />
            </AuthProvider>
        </BrowserRouter>
    );
}

export default App
