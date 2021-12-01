import { User } from "../../interfaces/User";
import { AuthActionTypes } from "./auth.action.types";

interface Role {
  id: number,
  name: string
}

export interface UserJwtData{
  exp?: number,
  iat?: number,
  userId?: number,
  sub?: string,
  role?: Role,
}

export interface UserState {
  isLoggedIn: boolean,
  user?: User
}

interface Action {
  type: string,
  payload?: UserState
}

const user : User = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')!) : null

const initialState : UserState = user 
  ? { isLoggedIn: true, user: {...user}}
  : { isLoggedIn: false, user: null!}

  export const authReducer = (state: UserState = initialState, action: Action) => {
    switch (action.type) {
      case AuthActionTypes.LOGIN_SUCCESS:
        return {
          ...state,
          isLoggedIn: true,
          user: action.payload
        }
        case AuthActionTypes.LOGIN_FAIL:
          return {
            ...state,
            isLoggedIn: false,
            user: null
          }
        case AuthActionTypes.LOGOUT:
          return {
            ...state,
            isLoggedIn: false,
            user: null
          }
        case AuthActionTypes.REGISTER_SUCCESS:
          return {
            ...state,
            isLoggedIn: false,
          }
        case AuthActionTypes.REGISTER_FAIL:
          return {
            ...state,
            isLoggedIn: false,
          }
        
        default:  
          return state
    }
    
  }