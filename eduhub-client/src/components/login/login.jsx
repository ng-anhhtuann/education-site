import React from 'react';
import Back from "../common/back/Back"
import { Link } from "react-router-dom"
import './login.css';

const Login = () => {
    return (
        <>
            <Back title='Login' />
            <section className="login-container">
                <form className="login-form">
                    <h2>Login</h2>
                    <input type="text" placeholder="Username" />
                    <input type="password" placeholder="Password" />
                    <Link to="/register">Don't have an account? Register here</Link>
                    <button type="submit">Login</button>
                </form>
            </section>
        </>

    );
};

export default Login;

