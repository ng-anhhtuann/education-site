import { FileAPI, API } from "./api";

const FileService = {
  uploadImage: (file) => {
    const formData = new FormData();
    formData.append("image", file);
    formData.append("file", file);

    return FileAPI.post("file/upload-image?image", formData)
      .then((res) => {
        console.log(res);
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  uploadVideo: (e) => {
    return FileAPI.post("file/upload-video", e)
      .then((res) => {
        console.log(res);
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  deleteFileByFileName: (fileName) => {
    return API.delete("file/delete-file", {
      params: {
        fileName: fileName,
      },
    })
      .then((res) => {
        console.log(res);
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },
};

export default FileService;
