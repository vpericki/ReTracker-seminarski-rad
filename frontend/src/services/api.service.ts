import axios from "axios"

const API_ENDPOINT = 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_ENDPOINT
})

export default api
