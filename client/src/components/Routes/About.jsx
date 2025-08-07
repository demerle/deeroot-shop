import ytlogo from "../../assets/ytlogo.png";

export default function About() {
    return (
        <main>
            <div className="welcome">
                <h1>About Me Page</h1>

                <h3>Hey there, My name is Dan Emerle.


                <strong>For those of which are looking at this coming from LinkedIn etc:</strong>
                    I am a Computer Science Student at NJIT in my 3rd year, and I am also an aspiring software engineer.

                    This is my most involved project so far is a display of all of all of the learning and work I put in
                    throughout Summer 2025.




                </h3>



                <br/>
                <br/>
                <h4>Make sure to check out my Youtube Here: </h4>
                <a href="https://www.youtube.com/@Deeroot">
                    <img
                        src={ytlogo}
                        style={{height: "10%"}}
                    />
                </a>


            </div>
        </main>
)
}