import { API } from "./api";

const ImageService = {
  searchImageByCondition: (e) => {
    return API.post("image/list", e)
      .then((res) => {
        if (res.data.status === 200) {
          sessionStorage.setItem("SEARCH_IMAGE", JSON.stringify(e));
          sessionStorage.setItem(
            "IMAGE_LIST_BY_OWNER_ID",
            JSON.stringify(res.data.data.datas) || []
          );
          sessionStorage.setItem(
            "IMAGE_COUNT_BY_OWNER_ID",
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

  createOrUpdateImage: (e) => {
    return API.post("image/edit", e)
      .then(({ res }) => {
        console.log({ res });
        setStorage(res.data.id);
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err.response.data;
      });
  },

  getImageById: (id) => {
    return API.get(`image/detail/${id}`)
      .then((res) => {
        console.log(res)
        if (res.data.status === 200) {
          const img = JSON.stringify(res.data.data);
          setStorage(img);
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
  sessionStorage.setItem("CURRENT_IMAGE", data);
};

export default ImageService;
