import { AxiosRequestConfig } from "axios"
import { useDispatch } from "react-redux"
import { useNavigate } from "react-router"
import api from "../services/api.service"
import { logout } from "../state/auth/auth.action.creators"
import { useAppThunkDispatch } from "../state/store"

const Interceptors = () => {

    const dispatch = useAppThunkDispatch()
    const navigate = useNavigate()

    api.interceptors.request.use((config: AxiosRequestConfig) => {
        const token: string | null = localStorage.getItem('token')
        
        if (token) {
            config.headers = {
              Authorization: `Bearer ${token}`
             } 
           }
      
        return config
      })
    
      api.interceptors.response.use(response => {
        if (response.status === 403 || response.status === 401) {
          dispatch(logout())
            .then(() => {
              navigate('/forbidden')
            })
        }
    
        return response
      })

    return null
}

export default Interceptors
