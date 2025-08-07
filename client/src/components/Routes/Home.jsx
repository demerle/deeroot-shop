import Main from "../Main.jsx";
import NavBar from "../NavBar.jsx";
import DropDown from "../DropDown.jsx";
import ytlogo from "../../assets/ytlogo.png";
import {Link} from "react-router-dom";
export default function Home(props) {


    return (
        <>
            <Main musicItems={props.musicItems}/>

            <div className="solo-text-container about">
                <Link className = "solo-text" to={"/about"}><h2>About Me</h2></Link>
            </div>

        </>
    )
}