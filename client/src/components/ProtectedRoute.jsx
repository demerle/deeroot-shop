import {Navigate} from "react-router-dom";
import {useAuth} from "./AuthContext.jsx";

export default function ProtectedRoute({children, allowedRoles}) {

    const { user } = useAuth()

    if (user.role === "GUEST"){
        return <Navigate to={"/login"} replace />
    }

    if (!allowedRoles.includes(user.role)){
        return <Navigate to={"/unauthorized"} replace />
    }

    return children
}