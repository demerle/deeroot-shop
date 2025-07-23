import {useNavigate} from "react-router-dom";

export default function Cancel(){

    const navigate = useNavigate();

    alert("Payment was not successful")
    navigate("/")

}