import ytlogo from "../assets/ytlogo.png";
import {Link} from "react-router-dom";


export default function Welcome(){
    return (
        <div className="welcome">
            <h1>Welcome to DeerootShop!</h1>
            <div className="solo-text-container about">
                <Link className="solo-text" to={"/about"}><h2>About Me</h2></Link>
            </div>

        </div>
    )
}


/*


 */