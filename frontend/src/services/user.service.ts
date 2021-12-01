import { AxiosResponse } from 'axios'
import { UserRegister } from "../interfaces/UserRegister";
import { User } from "../interfaces/User";
import api from './api.service';

interface LoginResponse {
  data: {token: string},
  error: string
}

interface UserResponse {
  data: User,
  error: string
}

export interface UserRegisterResponse {
  data?: {
    id: number,
    username: string,
    firstName: string,
    lastName: string,
    dateOfBirth: Date,
    email: string,
    rating: number,
    roleDto: {
      id: number,
      name: string
    }
    companyDto: {}
  },
  error?: string
}

export const userService = {
    login: (username: string, password: string): Promise<AxiosResponse<LoginResponse>> =>{
        return api.post('/auth/login',
        {
          username: username,
          password: password
        }
      )
    },
    register: (registerData: UserRegister) : Promise<AxiosResponse<UserRegisterResponse>> => {
      return api.post(`/auth/register`, { ...registerData })
    },
    logout: () : void => {
      localStorage.removeItem('user')
      localStorage.removeItem('token')
    },
    user: (id: number) : Promise<AxiosResponse<UserResponse>> => {
      return api.get(`/user/${id}`)
    }
  }