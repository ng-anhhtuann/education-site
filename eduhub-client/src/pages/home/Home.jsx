import React from "react"
import Heading from "../../shared/components/common/heading/Heading"
import "./home.css"
import Header from "../../shared/components/common/header/Header"

const Home = () => {
  const homeAbout = [
    {
      id: 1,
      cover: "https://img.icons8.com/dotty/80/000000/storytelling.png",
      title: "Online Courses",
      desc: "Improve your seft-taught skill and earn more experience throughout organizational courses.",
    },
    {
      id: 1,
      cover: "https://img.icons8.com/ios/80/000000/diploma.png",
      title: "Earn A Certificates",
      desc: "Earn certificate each course for cop more badge and further upgrade your CV.",
    }
  ]
  
  return (
    <>
    <Header />
      <section className='aboutHome'>
        <div className='container flexSB'>
          <div className='left row'>
            <img src='./images/about.webp' alt='' />
          </div>
          <div className='right row'>
            <Heading subtitle='LEARN CODE' title='ONLINE CODE LEARNING AND EVERYTHING' />
            <div className='items'>
              {homeAbout.map((val, index) => {
                return (
                  <div className='item flexSB'>
                    <div className='img'>
                      <img src={val.cover} alt='' key={index}/>
                    </div>
                    <div className='text' key={index}>
                      <h2>{val.title}</h2>
                      <p>{val.desc}</p>
                    </div>
                  </div>
                )
              })}
            </div>
          </div>
        </div>
      </section>
    </>
  )
}

export default Home
