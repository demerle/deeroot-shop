import Login, { Render } from 'react-login-page';
import Logo from 'react-login-page/logo-rect';
import React from 'react';
export default function LoginPage() {
    return (
        <form id="form" className="login-form">
            <label>Username:</label>
            <input type="text" id="username" name="username" placeholder="Ex: Sandwich123"/>

            <label>Password:</label>
            <input
                type="password"
                id="password"
                name="password"
                placeholder="Ex: ********"
                title = "Password must contain at least 5 characters"
            />
        </form>
    )
}