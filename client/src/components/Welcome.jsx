import ytlogo from "../assets/ytlogo.png";
import {Link} from "react-router-dom";


export default function Welcome(){
    return (
        <div className="welcome">
            <h1>Welcome to DeerootShop!</h1>
            <h3 className={"welcome-h3"}>This is my personal store for my youtube content</h3>
            <br/>
            <div className="solo-text-container about">
                <Link className="solo-text" to={"/about"}><h2>About Me</h2></Link>
            </div>

        </div>
    )
}


/*


 */