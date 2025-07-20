
import React from "react";
import {BrowserRouter, Routes, Route, useLocation} from "react-router-dom";
import LoginPage from "./components/Routes/LoginPage.jsx";
import Home from "./components/Routes/Home.jsx";
import CreateAccountPage from "./components/Routes/CreateAccountPage.jsx";
import About from "./components/Routes/About.jsx";
import Profile from "./components/Routes/Profile.jsx";
import NavBar from "./components/NavBar.jsx";
import Admin from "./components/Routes/Admin.jsx";
import Unauthorized from "./components/Routes/Unauthorized.jsx";
import {AuthProvider} from "./components/AuthContext.jsx";
import Logout from "./components/Routes/Logout.jsx";
import ProtectedAdminRoute from "./components/ProtectedRoute.jsx";
function AppRoutes() {



    const location = useLocation()
    const hideNavBarRoutes = ['/login', '/create-account']
    const shouldHideNavBar = hideNavBarRoutes.includes(location.pathname);

   // const {user, setUser} = useAuth()

    return (
    <>

            {!shouldHideNavBar && <NavBar />}
            <Routes>
                <Route path="/" element={<Home />} />
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
