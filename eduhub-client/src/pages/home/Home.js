import React from "react";
import Heading from "../../shared/components/common/heading/Heading";
import "./main.css";
import SearchBar from "./SearchBar";
import PrimarySearchAppBar from "./NavBar";

const Home = () => {
  return (
    <>
      <PrimarySearchAppBar />
      <SearchBar />
      <section className="aboutHome">
        <div className="container flexSB">
          {/* <div className="left row">
            <img src="./images/about.webp" alt="" />
          </div> */}
          <div className="right row">
            <Heading
              subtitle="LEARN CODE"
              title="ONLINE CODE LEARNING AND EVERYTHING"
            />
          </div>
        </div>
      </section>
    </>
  );
};

export default Home;
