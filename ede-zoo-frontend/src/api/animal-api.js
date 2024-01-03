import axios from 'axios';
import ConfigData from '../config.json';

const animalBaseUrl = ConfigData.zooBaseUrl + "/animal"

class AnimalApi {

    static getAllAnimals() {
        return axios.get(animalBaseUrl);
    }

}

export default AnimalApi;