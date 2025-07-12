export default function DropDown() {


    const loggedIn = false
    return (
        <div className="dropdown" id="profileDropdown">
            <a href="http://localhost:5173/profile">My Profile</a>
            {!loggedIn && <a href="http://localhost:5173/login">Log in</a>}
            {loggedIn && <a href="#">Log out</a>}
        </div>
    )
}