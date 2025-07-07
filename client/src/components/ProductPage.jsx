import sheet from "../assets/sheet.jpg"

export default function ProductPage() {
    return (
        <>
            <img src = {sheet} />
            <div className="product-details">
                <h1>title</h1>
                <p>description</p>
                <p>price</p>
                <button>Buy</button>

                {/*

                // If user Owns Sheet/Midi, Conditionally render the download button instead of buy, and remove the price as well


                */}
            </div>
        </>
    )
}