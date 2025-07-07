import React from "react";

export default function CreateAccountPage() {
    return (
        <form id="form" className="login-form">
            <label>Create Username:</label>
            <input type="text" id="username" name="username" placeholder="Ex: Sandwich123"/>

            <label>Create Password:</label>
            <input
                type="password"
                id="password"
                name="password"
                placeholder="Ex: ********"
                title="Password must contain at least 5 characters"
            />

            <label>Retype Password:</label>
            <input
                type="password"
                id="password"
                name="password"
                placeholder="Ex: ********"
                title="Password must contain at least 5 characters"
            />

            <label>Enter Email:</label>
            <input type="text" id="email" name="email" placeholder="Ex: dumb@gmail.com"/>

        </form>
    )
}