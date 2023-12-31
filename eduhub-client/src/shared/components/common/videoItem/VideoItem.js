import React, { useState, useMemo } from "react";
import "./video.css";
import thumbnail from "./player.webp";
import Toast from "../../Toast";
import VideoService from "../../../service/videoService";
import { useNavigate } from "react-router-dom";

const VideoItem = ({ video }) => {
  const navigate = useNavigate();
  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");
  const search = {
    page: 1,
    pageSize: 0,
    searchType: "FIELD",
    params: {
      course_id: sessionStorage.getItem("COURSE_CLICK"),
    },
  };
  
  const onClickPreview = (e) => {
    VideoService.searchVideoByCondition(search)
      .then((res) => {
        if (res.data.status !== 200) {
          setTypeNotify("error");
          setTextNotify(res.data.errors || "Something went wrong");
          setIsNotify(true);
          return;
        }
      })
      .catch((err) => {
        setTypeNotify("error");
        setTextNotify("Something went wrong");
        setIsNotify(true);
      });
    VideoService.getVideoById(video.id);
    navigate("/video");
  };

  if (isNotify) {
    setTimeout(() => {
      if (typeNotify !== "info") {
        if (typeNotify === "success") {
          window.location.reload();
        }
        setIsNotify(false);
        setTextNotify("");
      }
    }, 2000);
  }

  const toast = useMemo(() => {
    return <Toast isNotify={isNotify} text={textNotify} type={typeNotify} />;
  }, [isNotify, textNotify, typeNotify]);

  return (
    <div className="video-container" onClick={onClickPreview}>
      {toast}
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
