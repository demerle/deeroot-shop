import Welcome from "./Welcome.jsx";
import MusicItem from "./MusicItem.jsx";

export default function Main(props) {

    return (
        <main>
            <Welcome/>

            <div className="product-container">

                {props.musicItems && props.musicItems.map((musicItem) => (
                    <MusicItem item = {musicItem} key={musicItem.id} title={musicItem.title} price={musicItem.price}/>
                ))}
            </div>
        </main>
    )
}
