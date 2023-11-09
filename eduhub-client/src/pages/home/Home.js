import React from "react"
import Heading from "../../shared/components/common/heading/Heading"
import "./main.css"

const Home = () => {
    const homeAbout = [
      {
        id: 1,
        cover: "https://img.icons8.com/dotty/80/000000/storytelling.png",
        title: "Online Courses",
        desc: "Enroll in organizational courses to improve your self-taught skills, gain experience, and earn certificates with badges to enhance your professional profile.",
      },
      {
        id: 1,
        cover: "https://img.icons8.com/ios/80/000000/diploma.png",
        title: "Earn A Certificates",
        desc: "By earning certificates for each course, you can enhance your CV with more badges, showcasing your expertise and dedication to continuous learning.",
      }
    ]
    
    return (
      <>
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
                    <div className='item flexSB' key={index}>
                      <div className='img'>
                        <img src={val.cover} alt=''/>
                      </div>
                      <div className='text'>
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