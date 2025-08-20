import ytlogo from "../../assets/ytlogo.png";

export default function About() {
    return (
        <main>
            <div className="welcome">
                <u><h1>About Me</h1></u>
                <br/>

                <h3>Hey there, My name is Dan Emerle
                    <br/><br/>
                    I am a Computer Science student at NJIT in my 3rd year, and an aspiring software engineer.
                    <br/><br/>
                    Some of my hobbies include playing the piano/making piano covers, coding, video games, content-creation, anime, volleyball, and reading.
                    <br/><br/>
                    This is my most involved programming project so far and is a display of all of the learning and work I put in
                    throughout Summer 2025, so if you would like to support me please buy some sheets.
                </h3>

                <br/><br/>
                <h4>Make sure to check out my Youtube here: </h4>
                <a href="https://www.youtube.com/@Deeroot">
                    <img
                        src={ytlogo}
                    />
                </a>
            </div>
        </main>
)
}