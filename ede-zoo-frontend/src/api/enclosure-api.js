import axios from 'axios';
import ConfigData from '../config.json';

const enclosureBaseUrl = ConfigData.zooBaseUrl + "/enclosure"

class EnclosureApi {

    static getAllEnclosures() {
        return axios.get(enclosureBaseUrl);
    }
    
    static getEnclosureById(id) {
        return axios.get(`${enclosureBaseUrl}/byId/${id}`);
    }

    static createEnclosure(enclosureRequest, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        };

        return axios.post(enclosureBaseUrl, enclosureRequest, config)
    }

    static updateEnclosure(id, enclosureRequest, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
        };

        return axios.put(`${enclosureBaseUrl}/${id}`, enclosureRequest, config);
    }

    static deleteEnclosure(id, token) {
        const config = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }

        return axios.delete(`${enclosureBaseUrl}/${id}`, config);
    }

}

export default EnclosureApi;