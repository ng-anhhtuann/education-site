import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/",
  headers: {
    Accept: "application/json",
  },
});

const FileAPI = axios.create({
  baseURL: "https://eduhub.fly.dev/",
  headers: {
    Accept: "application/json",
    "Content-Type": "multipart/form-data",
  },
});

// Add response interceptors for both instances if needed
API.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    throw err;
  }
);

FileAPI.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    throw err;
  }
);

export { API, FileAPI };
