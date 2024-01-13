import React, { useState, useMemo } from "react";
import "./videoEditItem.css";
import thumbnail from "./player.webp";
import Toast from "../../Toast";
import VideoService from "../../../service/videoService";
import { useNavigate } from "react-router-dom";

const VideoEditItem = ({ video }) => {
  const navigate = useNavigate();
  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");
  const [option, setOption] = useState(false);
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

  const onClickDelete = (e) => {
    VideoService.deleteVideoById(video.id)
      .then((res) => {
        if (res.data.status !== 200) {
          setTypeNotify("error");
          setTextNotify(res.data.errors || "Something went wrong");
          setIsNotify(true);
          return;
        } else {
          setTypeNotify("success");
          setTextNotify("Video deleted successfully!");
          setIsNotify(true);
        }
      })
      .catch((err) => {
        setTypeNotify("error");
        setTextNotify("Something went wrong");
        setIsNotify(true);
      });
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

  return option ? (
    <div className="video-edit-option">
      {toast}
      <div className="video-preview" onClick={onClickPreview}>
        <h1>PREVIEW VIDEO</h1>
      </div>
      <div className="video-delete" onClick={onClickDelete}>
        <h1>DELETE VIDEO</h1>
      </div>
    </div>
  ) : (
    <div className="video-edit-container" onClick={() => setOption(!option)}>
      {toast}
      <div className="video-edit-parent">
        <img src={thumbnail} alt="" />
      </div>
      <div className="video-text-container">
        <h1>{video.title}</h1>
        <h2>{video.description}</h2>
      </div>
    </div>
  );
};

export default VideoEditItem;
