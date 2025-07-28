import ytlogo from "../../assets/ytlogo.png";

export default function About() {
    return (
        <>
            <h1>About Me Page</h1>

            <p>Hello, this is a filler paragraph for when I actually create my about me page.
            This webApp really took me a long time to make so pls download some midi or something</p>

            <br/>
            <br/>
            <h4>Make sure to check out my Youtube Here: </h4>
            <a href="https://www.youtube.com/@Deeroot">
                <img
                    src={ytlogo}
                    style={{height: "10%"}}
                />
            </a>


        </>
)
}