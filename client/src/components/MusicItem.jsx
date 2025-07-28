import {Document, Page} from "react-pdf"
import {useNavigate} from "react-router-dom";
export default function MusicItem(props){


    const navigate = useNavigate();

    const image = props.img === "" ? null : props.img;

    function reNavigate(){
        navigate("/product/" + props.item.id)
    }

    return (
        <div className="music-item" onClick={reNavigate}>
            <img
                src = {image}
                alt={"Cant find image"}
            />
            <p>{props.title}</p>
            {props.price && <p>{props.price}</p>}
        </div>
    )
}