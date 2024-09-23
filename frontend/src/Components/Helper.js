import axios from "axios";
export const BASE_URL = 'https://sarthakdev-is-banking.onrender.com';
export const MYAXIOS = axios.create({ baseURL: BASE_URL })