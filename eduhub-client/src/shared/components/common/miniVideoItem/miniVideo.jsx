import React from "react";
import "./miniVideo.css";
import { PlayArrow } from "@mui/icons-material";

const MiniVideoItem = ({ video, onChoose = false, onClick }) => {
  const containerStyle = {
    backgroundColor: onChoose ? "lightblue" : "white",
  };

  return (
    <div className="mini-video-container" style={containerStyle} onClick={onClick}>
      <div className="mini-video-parent">
        <PlayArrow className="play" viewBox="0 0 24 24"/>
      </div>
      <h1>{video.title}</h1>
    </div>
  );
};

export default MiniVideoItem;
