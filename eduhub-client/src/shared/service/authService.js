import API from './api';

const AuthService = {
    login: (e) => {
        return API.post('user/login', e)
            .then((res) => {
                console.log(res)
                const id = res.data.data.id
                setHeadersAndStorage(id);
                return res;
            })
            .catch((err) => {
                console.log(err);
                throw err;
            });
    },

    register: (e) => {
        return API.post('user/edit', e)
            .then(({ res }) => {
                console.log({res});
                setHeadersAndStorage(res.data.id);
                return res;
            })
            .catch((err) => {
                console.log(err);
                throw err.response.data;
            });
    },
};

const setHeadersAndStorage = (id) => {
    sessionStorage.setItem('ID', id); // Set userId in session storage
};

export default AuthService;