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
            <button>Sheets</button>
            <button>Midi</button>
            {//<SearchBar />
            }
            <SearchBar />
            <img
                src={user}
            />

        </header>
    )
}