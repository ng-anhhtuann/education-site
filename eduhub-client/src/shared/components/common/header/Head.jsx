import React from "react"
import "../footer/footer.css";

const Head = () => {
  return (
    <>
      <section className='head'>
        <div className='container flexSB'>
          <div className='logo'>
            <h1>EDUHUB</h1>
            <span>EDUCATION SITE</span>
          </div>

          <div className="footer-social">
          <i className="fab fa-facebook-f icon"></i>
          <i className="fab fa-twitter icon"></i>
          <i className="fab fa-instagram icon"></i>
        </div>
        </div>
      </section>
    </>
  )
}

export default Head
