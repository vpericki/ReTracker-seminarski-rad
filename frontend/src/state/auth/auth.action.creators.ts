import { AnyAction } from 'redux'
import { ThunkDispatch } from 'redux-thunk'
import { UserRegister } from '../../interfaces/UserRegister'
import { UserRegisterResponse, userService } from '../../services/user.service'
import { MessageActionTypes } from '../message/message.action.types'
import { AppThunkAction, RootState } from '../store'
import { AuthActionTypes } from './auth.action.types'
import { UserJwtData } from './auth.reducer'

const parseJwt = (token: string) : UserJwtData => {
  let base64Url = token.split('.')[1];
  let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
};

export const login = (username: string, password: string) : AppThunkAction<Promise<void>>  => async (dispatch) : Promise<void>=> {
  return userService.login(username, password).then(
    async (data) => {
      const token = data.data.data.token
      const userDecodedJwt = parseJwt(token)
      localStorage.setItem('token', token)

      let user = (await userService.user(userDecodedJwt.userId!)).data.data
      user = {...user, token: token}

      dispatch({
        type: MessageActionTypes.CLEAR_MESSAGE
      })

      localStorage.setItem('user', JSON.stringify(user))

      dispatch({
        type: AuthActionTypes.LOGIN_SUCCESS,
        payload: { ...user}
      })

      return Promise.resolve()
    },
    (error) => {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.error) ||
        error.message ||
        error.toString();

        dispatch({
          type: AuthActionTypes.LOGIN_FAIL
        })

        dispatch({
          type: MessageActionTypes.SET_MESSAGE,
          payload: message
        })

        return Promise.reject()
    }
  )
}

export const logout = () => (dispatch: ThunkDispatch<RootState, unknown, AnyAction>) : Promise<void> => {
  userService.logout()

  dispatch({
    type: AuthActionTypes.LOGOUT
  })

  return Promise.resolve()
}


export const register = (userData: UserRegister) : AppThunkAction<Promise<UserRegisterResponse>> => async (dispatch) : Promise<UserRegisterResponse> => {
  return userService.register(userData)
    .then(data => data.data)
    .then((data: UserRegisterResponse) => {
      dispatch({
        type: MessageActionTypes.CLEAR_MESSAGE
      })

      dispatch({
        type: AuthActionTypes.REGISTER_SUCCESS,
      })

      return Promise.resolve(data)
    })
    .catch(error => {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.error) ||
        error.message ||
        error.toString();

        dispatch({
          type: AuthActionTypes.REGISTER_FAIL
        })

        dispatch({
          type: MessageActionTypes.SET_MESSAGE,
          payload: message
        })

        return Promise.reject()
    })
} 