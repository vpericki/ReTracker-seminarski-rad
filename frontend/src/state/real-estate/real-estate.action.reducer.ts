import { RealEstate } from "../../interfaces/RealEstate";
import { RealEstateActionTypes } from "./real-estate.action.types";


export interface RealEstateState {
  userListings: RealEstate[]
}

interface Action {
  type: string,
  payload?: RealEstateState | number
}

const initialState: RealEstateState = {
  userListings: []
}

export const RealEstateReducer = (state: RealEstateState = initialState, action: Action) => {
  switch (action.type) {
    case RealEstateActionTypes.GET_USER_LISTINGS:
      return {
        ...state,
        userListings: action.payload
      }
    case RealEstateActionTypes.DELETE_USER_LISTING:
      return {
        ...state,
        userListings: state.userListings.filter(ul => ul.id !== action.payload as number)
      }

    default:
      return state
  }
}