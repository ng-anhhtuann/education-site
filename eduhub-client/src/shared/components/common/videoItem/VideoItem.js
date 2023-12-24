import React from "react";
import "./video.css";
import thumbnail from "./player.webp"

const VideoItem = ({ video, onClick }) => {
  return (
    <div className="video-container" onClick={onClick}>
      <div className="video-parent">
        <img src={thumbnail} alt="" />
      </div>
      <div className="video-text-container">
        <h1>{video.title}</h1>
        <h2>{video.description}</h2>
      </div>
    </div>
  );
};

export default VideoItem;
