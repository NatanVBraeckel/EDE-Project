import axios from 'axios';
import ConfigData from '../config.json';

const foodBaseUrl = ConfigData.zooBaseUrl + "/food"

class FoodApi {

    static getAllFood() {
        return axios.get(foodBaseUrl);
    }

}

export default FoodApi;