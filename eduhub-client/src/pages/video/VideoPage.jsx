import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./video.css";
import ReactPlayer from "react-player";
import MiniVideoItem from "../../shared/components/common/miniVideoItem/miniVideo";

const VideoPage = () => {
  const [id, setId] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [currentVideo, setCurrentVideo] = useState({});
  const [videoList, setVideoList] = useState([]);

  useEffect(() => {
    setTimeout(() => {
      const storedCurrentVideo = JSON.parse(
        sessionStorage.getItem("CURRENT_VIDEO")
      );
      setCurrentVideo(storedCurrentVideo);
      setId(storedCurrentVideo.id);

      const storedVideoList = JSON.parse(
        sessionStorage.getItem("VIDEO_LIST_BY_COURSE_ID")
      );
      setVideoList(storedVideoList);
      setIsLoading(false);
    }, 1000);
  }, []);

  function formatDate(unixTime) {
    const date = new Date(unixTime);
    const options = {
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
      day: "2-digit",
      month: "short",
      year: "numeric",
    };
    return date.toLocaleDateString("en-US", options);
  }

  const videoClick = (data) => {
    setCurrentVideo(data);
    setId(data.id);
    sessionStorage.setItem("CURRENT_VIDEO", JSON.stringify(data));
  };

  return (
    <Layout>
      <section className="about">
        <div className="videoList">
          <div className="videoListContainer">
            {isLoading ? (
              <p>Loading...</p>
            ) : (
              videoList.map((data) => (
                <MiniVideoItem
                  key={data.id}
                  video={data}
                  onChoose={data.id === id}
                  onClick={() => videoClick(data)}
                />
              ))
            )}
          </div>
          <div className="videoPlayer">
            <ReactPlayer
              url={currentVideo.url}
              controls
              width="100%"
              height="80%"
            >
              Your browser does not support the video tag.
            </ReactPlayer>
            <h1>{currentVideo.title}</h1>
            <h2>{currentVideo.description}</h2>
            <h3>Created at: {formatDate(currentVideo.createdDate)}</h3>
          </div>
        </div>
      </section>
    </Layout>
  );
};

export default VideoPage;
