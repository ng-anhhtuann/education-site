import React, { useState } from "react";
import { Link } from "react-router-dom";
import Head from "./Head";
import "./header.css";

const Header = ({ hidden = false, route, routeName }) => {
  const [click, setClick] = useState(false);

  return (
    <>
      <Head />
      {hidden ? (
        <></>
      ) : (
        <header>
          <nav className="flexSB">
            <ul
              className={click ? "mobile-nav" : "flexSB "}
              onClick={() => setClick(false)}
            >
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/contact">Contact</Link>
              </li>
            </ul>
            <div className="start">
              <div className="button">
                <li>
                  <Link to={route}>{routeName}</Link>
                </li>
              </div>
            </div>
            <button className="toggle" onClick={() => setClick(!click)}>
              {click ? (
                <i className="fa fa-times"> </i>
              ) : (
                <i className="fa fa-bars"></i>
              )}
            </button>
          </nav>
        </header>
      )}
    </>
  );
};

export default Header;
