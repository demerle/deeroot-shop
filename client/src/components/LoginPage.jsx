import React from 'react';
export default function LoginPage() {


    async function onSubmit(email, password) {

        try {
            const response = await fetch('http://localhost:8080/auth', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({email, password})
            });

            if (!response.ok) {
                throw new Error(`Login Failed: ${response.status}`);
            }

            const data = await response.json();
            console.log("Login Success:", data);
            return data;
        } catch (error) {
            console.error("Error Logging in:", error);
            throw error;
        }
    }


    return (
        <>
            <form id="form" className="login-form">
                <label>Email:</label>
                <input type="text" id="email" name="email" placeholder="Ex: dumb@dumb.com"/>

                <label>Password:</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Ex: ********"
                    title="Password must contain at least 5 characters"
                />
            </form>

            <button onClick={onSubmit} type={"submit"} id="submit">Submit</button>

            <a href="http://localhost:5173/create-account">Not Logged In? Create an Account Here</a>
        </>
    )
}