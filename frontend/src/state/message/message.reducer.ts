import { MessageActionTypes } from "./message.action.types"

interface Action {
  type: string,
  payload: string
}

export interface MessageState {
  message: string;
}

const initialState: MessageState = {
  message: ""
}

export const messageReducer = (state: MessageState = initialState, action: Action) => {
  switch (action.type) {
    case MessageActionTypes.SET_MESSAGE:
      return {
        message: action.payload
      }
    case MessageActionTypes.CLEAR_MESSAGE:
      return {
        message: ""
      }
    default:
      return state
  }
}