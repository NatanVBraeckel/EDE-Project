import axios from 'axios';
import ConfigData from '../config.json';

const animalBaseUrl = ConfigData.zooBaseUrl + "/animal"

class AnimalApi {

    static getAllAnimals() {
        return axios.get(animalBaseUrl);
    }

    static getAnimalById(id) {
        return axios.get(`${animalBaseUrl}/byId/${id}`);
    }

    static createAnimal(animalRequest, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        };

        return axios.post(animalBaseUrl, animalRequest, config)
    }

    static updateAnimal(id, animalRequest, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        };

        return axios.put(`${animalBaseUrl}/${id}`, animalRequest, config);
    }

    static deleteAnimal(id, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }

        return axios.delete(`${animalBaseUrl}/${id}`, config);
    }
}

export default AnimalApi;