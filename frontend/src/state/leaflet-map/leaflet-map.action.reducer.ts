import { LatLng, LatLngBounds } from "leaflet";
import { RealEstate } from "../../interfaces/RealEstate";
import { LeaftletMapActionTypes } from "./leaflet-map.action.types";

export interface MapState {
  bounds?: LatLngBounds,
  latLng?: LatLng,
  listing: RealEstate[],
  listingFromFilter: RealEstate[],
  showOnlyFromFilter?: boolean
}

interface Action {
  type: string,
  payload?: MapState
}

const initialState : MapState = {
  bounds: null!,
  latLng: null!,
  listing: [],
  listingFromFilter: [],
  showOnlyFromFilter: false
}

export const LeaftletMapReducer = (state: MapState = initialState, action: Action) => {
  switch (action.type) {
    case LeaftletMapActionTypes.INTERACTION_END:
      return {
        ...state,
        bounds: action.payload
      }
    case LeaftletMapActionTypes.CLEAR_BOUNDS:
      return {
        ...state,
        bounds: null!
      }
    case LeaftletMapActionTypes.SELECT_LOCATION:
      return {
        ...state,
        latLng: action.payload
      }
    case LeaftletMapActionTypes.ADD_REAL_ESTATE:
      return {
        ...state,
        listing: action.payload
      }
    case LeaftletMapActionTypes.FIND_FROM_FILTER:
      return {
        ...state,
        listing: action.payload
      }
    case LeaftletMapActionTypes.TOGGLE_SHOW_ONLY_FILTER:
      return {
        ...state,
        showOnlyFromFilter: action.payload
      }
    default: 
      return state
  }
}