import axios from 'axios';
import configData from '../config.json';

const getToken = async () => {
    const clientId = configData.clientId;
    const clientSecret = configData.clientSecret;
    const tokenUrl = configData.authServer;
  
    const params = new URLSearchParams();
    params.append('grant_type', 'client_credentials');
    params.append('client_id', clientId);
    params.append('client_secret', clientSecret);
  
    try {
      const response = await axios.post(tokenUrl, params, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });
  
      const accessToken = response.data.access_token;

      console.log("Response data:", response.data);

      return accessToken;
    } catch (error) {
      console.error('Error fetching access token:', error);
      throw error;
    }
  };

