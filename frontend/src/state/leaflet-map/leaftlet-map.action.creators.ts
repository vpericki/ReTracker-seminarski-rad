import { LatLng, LatLngBounds } from "leaflet";
import { RealEstateService } from "../../services/realestate.service";
import { AppThunkAction } from "../store";
import { LeaftletMapActionTypes } from "./leaflet-map.action.types";
import { RealEstateFilter } from '../../interfaces/RealEstate/RealEstateFilter'
import { RealEstate } from "../../interfaces/RealEstate";

export const saveBounds = (bounds: LatLngBounds) : AppThunkAction<Promise<void>> => async (dispatch, getState) : Promise<void> => {
  const { map }: any = getState()

  const lng1 = bounds.getNorthEast().lng
  const lng2 = bounds.getSouthWest().lng

  const lat1 = bounds.getNorthEast().lat
  const lat2 = bounds.getSouthWest().lat

  const filter: RealEstateFilter = {
    latitudeA: lat2,
    latitudeB: lat1,
    longitudeA: lng2,
    longitudeB: lng1
  }

  return RealEstateService.getAllRealEstate(0, 1000, filter)
    .then(response => response.data.data.content as RealEstate[])
    .then(realEstates => {
      
      const listing: RealEstate[] = map.listing
      const difference  = realEstates.filter(re => !listing.some(r => r.id === re.id))
      const combined = [...listing, ...difference]

      dispatch({
        type: LeaftletMapActionTypes.INTERACTION_END,
        payload: bounds
      })
      dispatch({
        type: LeaftletMapActionTypes.ADD_REAL_ESTATE,
        payload: combined
      })

      return Promise.resolve()
    })
    .catch(error => {
      return Promise.reject()
    })
 
}

export const clearBounds = () : AppThunkAction<void> => (dispatch) : void => {
  dispatch({
    type: LeaftletMapActionTypes.CLEAR_BOUNDS
  })
}

export const selectLocation = (latLng: LatLng) : AppThunkAction<void> => (dispatch) : void => {
  dispatch({
    type: LeaftletMapActionTypes.SELECT_LOCATION,
    payload: latLng
  })
}

export const findFromFilter = (filter: RealEstateFilter) : AppThunkAction<Promise<void>> => async (dispatch) : Promise<void> => {
  return RealEstateService.getAllRealEstate(0, 1000, filter)
    .then(response => response.data.data.content)
    .then(data => {
      dispatch({
        type: LeaftletMapActionTypes.FIND_FROM_FILTER,
        payload: data
      })
    })
    .catch(error => {
      return Promise.reject()
    })
}

export const toggleShowOnlyFromFilter = (state: boolean) : AppThunkAction<void> => (dispatch) : void => {
  dispatch({
    type: LeaftletMapActionTypes.TOGGLE_SHOW_ONLY_FILTER,
    payload: state
  })
}