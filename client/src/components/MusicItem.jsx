import {Document, Page} from "react-pdf"
import {useNavigate} from "react-router-dom";
import {convertPrice} from "../utils/utils.js";
import blurredSheet from "../assets/blurredSheetPage.jpg";
export default function MusicItem(props){


    const navigate = useNavigate();

    const image = props.img === "" ? blurredSheet : props.img;

    const price = convertPrice(props.price)

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
            <p>{price}</p>
        </div>
    )
}