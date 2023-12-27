import React, { useState, useMemo } from "react";
import Back from "../../shared/components/common/back/Back";
import { Link, useNavigate } from "react-router-dom";
import "./login.css";
import Toast from "../../shared/components/Toast";
import AuthService from "../../shared/service/authService"; // Import your AuthService
import Header from "../../shared/components/common/header/Header";

const LoginPage = () => {
  const navigate = useNavigate();

  const [account, setAccount] = useState(""); // State to manage the username input
  const [password, setPassword] = useState(""); // State to manage the password input
  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();
    const userData = {
      account,
      password,
    };

    AuthService.login(userData)
      .then((res) => {
        if (res.data.status !== 200) {
          setTypeNotify("error");
          setTextNotify(res.data.errors);
          return setIsNotify(true);
        } else {
          setTypeNotify("success");
          setTextNotify(`Login successfully!`);
          return setIsNotify(true);
        }
      })
      .catch((err) => {
        setTypeNotify("error");
        setTextNotify(err);
        return setIsNotify(true);
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
      <Header route={'/register'} routeName={"REGISTER"} hidden={false}/>
      <Back title="Login" />
      <section className="login-container">
        {toast}
        <form className="login-form">
          <h2>Login</h2>
          <input
            id="Username"
            type="text"
            placeholder="Username"
            value={account}
            onChange={(e) => setAccount(e.target.value)}
          />
          <input
            id="Password"
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Link to="/register">Don't have an account? Register here</Link>
          <button type="button" onClick={handleLogin}>
            Login
          </button>
        </form>
      </section>
    </>
  );
};

export default LoginPage;
