import { API } from "./api";

const UserService = {

  update: (e) => {
    return API.post("user/edit", e)
      .then((res) => {
        if (res.data.status === 200) {
          const id = res.data.data.id;
          const userData = JSON.stringify(res.data.data);
          setHeadersAndStorage(id, userData);
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  getUserById: (id) => {
    return API.get(`user/detail/${id}`)
      .then((res) => {
        if (res.data.status === 200) {
          const userData = JSON.stringify(res.data.data);
          setHeadersAndStorage(id, userData);
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },
};

const setHeadersAndStorage = (id, userData) => {
    sessionStorage.setItem("ID", id); // Set userId in session storage
    sessionStorage.setItem("USER_DATA", userData);
  };

export default UserService;
