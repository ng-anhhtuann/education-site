import React, { useState, useMemo } from "react";
import Back from "../../shared/components/common/back/Back";
import { Link, useNavigate } from "react-router-dom";
import "./register.css";
import Toast from "../../shared/components/Toast";
import AuthService from "../../shared/service/authService"; // Import your AuthService
import Header from "../../shared/components/common/header/Header";

const Register = () => {
  const navigate = useNavigate();

  const [userName, setUsername] = useState(""); // State to manage the username input
  const [password, setPassword] = useState(""); // State to manage the password input
  const [rePassword, setRePassword] = useState(""); // State to manage the re-password input
  const [email, setEmail] = useState(""); // State to manage the re-password input
  const [role, setRole] = useState("STUDENT"); // State to manage the re-password input
  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");

  const handleRegister = (e) => {
    e.preventDefault();
    const userData = {
      userName,
      password,
      rePassword,
      email,
      role,
    };

    AuthService.register(userData).then((res) => {
      if (res.data.status !== 200) {
        setTypeNotify("error");
        setTextNotify(res.data.errors);
        return setIsNotify(true);
      } else {
        setTypeNotify("success");
        setTextNotify(`Signup successfully!`);
        return setIsNotify(true);
      }
    });
  };

  if (isNotify) {
    setTimeout(() => {
      setIsNotify(false);
      if (typeNotify === "success") {
        navigate("/home");
      }
    }, 2000);
  }

  const toast = useMemo(() => {
    return <Toast isNotify={isNotify} text={textNotify} type={typeNotify} />;
  }, [isNotify, textNotify, typeNotify]);

  return (
    <>
      <Header route={'/login'} routeName={"LOGIN"} hidden={false}/>
      <Back title="Register" />
      <section className="register-container">
        {toast}
        <form className="register-form">
          <h2>Register</h2>
          <input
            type="text"
            placeholder="Username"
            value={userName}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <input
            type="password"
            placeholder="Confirm Password"
            value={rePassword}
            onChange={(e) => setRePassword(e.target.value)}
          />
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <div className="select-container">
            <label htmlFor="role">Select Role:</label>
            <select
              id="role"
              value={role}
              onChange={(e) => {
                setRole(e.target.value);
              }}
            >
              <option value="TEACHER">
                TEACHER - You can both upload courses and learn other teacher's
                courses.
              </option>
              <option value="STUDENT">
                STUDENT - You can just learn other teacher's courses.
              </option>
            </select>
          </div>

          <Link to="/login">Already had an account? Login here</Link>
          <button type="submit" onClick={handleRegister}>
            Register
          </button>
        </form>
      </section>
    </>
  );
};

export default Register;
