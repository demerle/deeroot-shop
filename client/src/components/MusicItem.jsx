import sheet from "../assets/sheet.jpg"
import {Document, Page} from "react-pdf"
export default function MusicItem(props){
    return (
        <div className="music-item">
            <img
                src = {sheet}
                style={{ height: "10%" }}
            />
            <p>{props.title}</p>
            <p>{props.price}</p>
        </div>
    )
}