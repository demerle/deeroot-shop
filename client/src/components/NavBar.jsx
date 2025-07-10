import dennisroot from "../assets/dennisroot.png"
import user from "../assets/user.png"
import SearchBar from "./SearchBar.jsx";

export default function NavBar() {
    return (
        <header className="navbar">
            <img
                src={dennisroot}
            />
            <h1>DeerootShop</h1>
            <div className={"navbar-middle"}>
                <button>Sheets</button>
                <button>Midi</button>
                {//<SearchBar />
                }
                <SearchBar />
            </div>
            <img className={"profile-image"}
                src={user}
            />

        </header>
    )
}