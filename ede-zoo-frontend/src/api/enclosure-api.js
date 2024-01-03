import axios from 'axios';
import ConfigData from '../config.json';

const enclosureBaseUrl = ConfigData.zooBaseUrl + "/enclosure"

class EnclosureApi {

    static getAllEnclosures() {
        return axios.get(enclosureBaseUrl);
    }

}

export default EnclosureApi;