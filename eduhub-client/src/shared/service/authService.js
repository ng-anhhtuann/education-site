import API from "./api";

const AuthService = {
  login: (e) => {
    return API.post("user/login", e)
      .then((res) => {
        console.log({ res });
        if (res.data.status === 200) {
          const id = res.data.data.id;
          setHeadersAndStorage(id);
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  register: (e) => {
    return API.post("user/edit", e)
      .then((res) => {
        console.log({e})
        console.log({ res });
        if (res.data.status === 200) {
          const id = res.data.data.id;
          setHeadersAndStorage(id);
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },
};

const setHeadersAndStorage = (id) => {
  sessionStorage.setItem("ID", id); // Set userId in session storage
};

export default AuthService;
