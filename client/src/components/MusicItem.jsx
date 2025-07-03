import sheet from "../assets/sheet.jpg"
import {Document, Page} from "react-pdf"
export default function MusicItem(props){
    return (
        <div className="MusicItem">
            <img
                src = {sheet}
                style={{ height: "20%" }}
            />
            <p>{props.title}</p>
            <p>{props.price}</p>
        </div>
    )
}