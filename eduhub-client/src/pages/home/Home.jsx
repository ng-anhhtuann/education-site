import React from "react"
import Heading from "../../shared/components/common/heading/Heading"
import "./home.css"
import { homeAbout } from "../../dummydata"

const Home = () => {
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
