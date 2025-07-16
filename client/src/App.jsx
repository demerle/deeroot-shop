
import React from "react";
import {BrowserRouter, Routes, Route, useLocation} from "react-router-dom";
import LoginPage from "./components/LoginPage.jsx";
import Home from "./components/Home.jsx";
import CreateAccountPage from "./components/CreateAccountPage.jsx";
import About from "./components/About.jsx";
import Profile from "./components/Profile.jsx";
import NavBar from "./components/NavBar.jsx";
import Admin from "./components/Admin.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import Unauthorized from "./components/Unauthorized.jsx";
import {AuthProvider} from "./components/AuthContext.jsx";
import Logout from "./components/Logout.jsx";
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
                    <ProtectedRoute allowedRoles={['ADMIN']}>
                        <Admin/>
                    </ProtectedRoute>
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
