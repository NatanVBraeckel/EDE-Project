import axios from 'axios';
import ConfigData from '../config.json';

const foodBaseUrl = ConfigData.zooBaseUrl + "/food"

class FoodApi {

    static getAllFood() {
        return axios.get(foodBaseUrl);
    }
    
    static getFoodById(id) {
        return axios.get(`${foodBaseUrl}/byId/${id}`)
    }

    static deleteFood(id, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }

        return axios.delete(`${foodBaseUrl}/${id}`, config);
    }

    static createFood(foodRequest, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        };

        return axios.post(foodBaseUrl, foodRequest, config)
    }

    static updateFood(id, foodRequest, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        };

        return axios.put(`${foodBaseUrl}/${id}`, foodRequest, config);
    }

    static updateStock(id, amount, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        };

        return axios.put(`${foodBaseUrl}/${id}/updateStock`, amount, config);
    }

}

export default FoodApi;