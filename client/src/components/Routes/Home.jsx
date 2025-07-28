import Main from "../Main.jsx";
import NavBar from "../NavBar.jsx";
import DropDown from "../DropDown.jsx";
import ytlogo from "../../assets/ytlogo.png";
export default function Home(props) {


    return (
        <>
            <Main musicItems={props.musicItems}/>
            <h2><u>About Me</u></h2>

        </>
    )
}