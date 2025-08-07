import Welcome from "./Welcome.jsx";
import MusicItem from "./MusicItem.jsx";
import ytlogo from "../assets/ytlogo.png";

export default function Main(props) {

    const empty = props.musicItems.length === 0

    const containerName = empty ? "solo-text-container" : "product-container"
    return (
        <main>
            <Welcome/>

            <div className={containerName}>

                {empty ? <h4 className="solo-text">No Results</h4> : props.musicItems.map((musicItem) => (
                    <MusicItem item={musicItem} key={musicItem.id} title={musicItem.title} price={musicItem.price}
                               img={musicItem.s3PreviewUrl} />
                ))}
            </div>

        </main>
    )
}
