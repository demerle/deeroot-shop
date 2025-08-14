import {Document, Page} from "react-pdf"
import {useNavigate} from "react-router-dom";
import {convertPrice} from "../utils/utils.js";
import blurredSheet from "../assets/blurredSheetPage.jpg";
import midi from "../assets/midi-modified.PNG";
export default function MusicItem(props){


    const navigate = useNavigate();

    let image = null
    let type = "Midi"
    if (props.item.fileType === "application/pdf"){
        image = props.img === "" ? blurredSheet : props.img;
        type = "Sheet"
    }
    else{
        image = midi
    }

    const price = props.price ? convertPrice(props.price) : false

    function reNavigate(){
        navigate("/product/" + props.item.id)
    }


    return (
        <div className="music-item" onClick={reNavigate}>
            <strong><p style={{color: "black", fontSize : "1vw"}}>{props.title}</p></strong>

            <img
                src={image}
                alt={"Cant find image"}
            />
            {price && <p>{price}</p>}
        </div>
    )
}