import sheet from "../assets/sheet.jpg"
import {Document, Page} from "react-pdf"
import {useNavigate} from "react-router-dom";
export default function MusicItem(props){


    const navigate = useNavigate();

    function reNavigate(){
        navigate("/product/" + props.item.id)
    }

    return (
        <div className="music-item">
            <img
                src = {sheet}
                style={{ height: "10%" }}
                onClick={reNavigate}
            />
            <p>{props.title}</p>
            {props.price && <p>{props.price}</p>}
        </div>
    )
}