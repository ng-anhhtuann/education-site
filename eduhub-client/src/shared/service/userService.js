import API from './api';

const UserService = {

    update: (e) => {
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

    getUserById: (id) => {
        return API.get(`user/detail/${id}`)
            .then(({ res }) => {
                console.log({res});
                return res;
            })
            .catch((err) => {
                console.log(err);
                throw err.response.data;
            });
    },

};

const setHeadersAndStorage = ({ id }) => {
    sessionStorage.setItem('ID', id); // Set userId in session storage
};

export default UserService;