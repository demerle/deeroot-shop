
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./components/LoginPage.jsx";
import Home from "./components/Home.jsx";
import CreateAccountPage from "./components/CreateAccountPage.jsx";
import About from "./components/About.jsx";
import Profile from "./components/Profile.jsx";
function App() {


    return (
    <>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/create-account" element={<CreateAccountPage />} />
                <Route path="/about" element={<About/>} />
                <Route path="/profile" element={<Profile/>} />
            </Routes>
        </BrowserRouter>
    </>
    )
}

export default App
