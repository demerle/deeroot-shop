import ytlogo from "../../assets/ytlogo.png";

export default function About() {
    return (
        <main>
            <div className="welcome">
                <u><h1>About Me</h1></u>
                <br/>

                <h3>Hey there, My name is Dan Emerle
                    <br/><br/>
                    I am a Computer Science Student at NJIT in my 3rd year, an aspiring software engineer, and a hobby musician. I play and create piano pieces and covers
                    and post them on my youtube channel.
                    <br/><br/>
                    This is my most involved project so far is a display of all of all of the learning and work I put in
                    throughout Summer 2025.

                </h3>

                <br/>
                <br/>
                <h4>Make sure to check out my Youtube Here: </h4>
                <a href="https://www.youtube.com/@Deeroot">
                    <img
                        src={ytlogo}
                    />
                </a>


            </div>
        </main>
)
}