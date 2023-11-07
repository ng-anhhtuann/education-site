import React from "react";
import "./footer.css";

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-logo">
          <h1>EDUHUB</h1>
          <span>EDUCATION SITE</span>
        </div>

        <div className="footer-description">
          <p>
            A formal small project for completing Java Programming in K20-FETEL HCMUS
          </p>
        </div>
        <div className="footer-legal">
        <p>
          Copyright © 2023 All rights reserved | This template is made with <i className="fa fa-heart"></i>
        </p>
      </div>
        <div className="footer-social">
          <i className="fab fa-facebook-f icon"></i>
          <i className="fab fa-twitter icon"></i>
          <i className="fab fa-instagram icon"></i>
        </div>
      </div>


    </footer>
  );
};

export default Footer;
