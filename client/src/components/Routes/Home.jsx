import Main from "../Main.jsx";
import NavBar from "../NavBar.jsx";
import DropDown from "../DropDown.jsx";
export default function Home(props) {


    return (
        <>
            { /*<NavBar/> */}
            <h2><u>About Me</u></h2>
            <Main musicItems = {props.musicItems} />
        </>
    )
}