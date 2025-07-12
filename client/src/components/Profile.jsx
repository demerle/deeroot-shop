import NavBar from "./NavBar.jsx";
import MusicItem from "./MusicItem.jsx";

export default function Profile() {



    //some useEffect to API call for all user elements
    const ownedSheets = [
        <MusicItem title={"Title1"} price = {"$0.00"}/>,
        <MusicItem title={"Title2"} price = {"$0.00"}/>
    ];

    return (
        <>
            <NavBar />
            <h1>Your Items</h1>
            <div className= "product-container">
                {ownedSheets}
            </div>
        </>
    )
}