import {useNavigate} from "react-router-dom";

export default function Unauthorized() {

    const navigate = useNavigate();

    function goHome(){
        navigate('/');
    }
    return (
        <>
            <h1>You Are Not Authorized to view this page</h1>
            <button onClick={goHome}>Home</button>
        </>
    )
}