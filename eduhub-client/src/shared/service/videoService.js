import { API } from "./api";

const VideoService = {
  searchVideoByCondition: (e) => {
    return API.post("video/list", e)
      .then((res) => {
        if (res.data.status === 200) {
          sessionStorage.setItem("SEARCH_VIDEO", JSON.stringify(e));
          sessionStorage.setItem(
            "VIDEO_LIST_BY_COURSE_ID",
            JSON.stringify(res.data.data.datas) || []
          );
          sessionStorage.setItem(
            "VIDEO_COUNT_BY_COURSE_ID",
            res.data.data.totalData || 0
          );
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  createOrUpdateVideo: (e) => {
    return API.post("video/edit", e)
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  getVideoById: (id) => {
    return API.get(`video/detail/${id}`)
      .then((res) => {
        console.log(res);
        if (res.data.status === 200) {
          const video = JSON.stringify(res.data.data);
          setStorage(video);
        }
        return res;
      })
      .catch((err) => {
        console.error(err);
        throw err;
      });
  },
};

const setStorage = (data) => {
  sessionStorage.setItem("CURRENT_VIDEO", data); // Set current video in-state in session storage
};

export default VideoService;
