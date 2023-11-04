import React from "react";
import Back from "../common/back/Back"
import "./register.css";

const Register = () => {
    return (
        <>
            <Back title='Login' />
            <section className="register-container">
                <form className="register-form">
                    <h2>Register</h2>
                    <input type="text" placeholder="Username" />
                    <input type="password" placeholder="Password" />
                    <input type="password" placeholder="Confirm Password" />
                    <button type="submit">Register</button>
                </form>
            </section>
        </>

    );
};

export default Register;
