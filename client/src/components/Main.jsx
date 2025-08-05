import Welcome from "./Welcome.jsx";
import MusicItem from "./MusicItem.jsx";
import ytlogo from "../assets/ytlogo.png";

export default function Main(props) {

    const empty = props.musicItems.length === 0

    return (
        <main>
            <Welcome/>

            <div className="product-container">

                {empty ? <h1>No Results</h1> : props.musicItems.map((musicItem) => (
                    <MusicItem item={musicItem} key={musicItem.id} title={musicItem.title} price={musicItem.price}
                               img={musicItem.s3PreviewUrl} />
                ))}
            </div>

        </main>
    )
}
