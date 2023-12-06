import React from "react";
import Layout from "../Layout";
import "./main.css"
import SearchSpace from "../../shared/components/common/search/SearchSpace";

const Home = () => {
  return (
    <Layout>
      <section className="about">
        <div className="contain">
          <div className="searchBar">
            <SearchSpace/>
          </div>
          <div className="searchRes">
            
          </div>
        </div>
      </section>
    </Layout>
  );
};

export default Home;
