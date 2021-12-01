import { RealEstateFilter } from "../../interfaces/RealEstate/RealEstateFilter";
import { RealEstateService } from "../../services/realestate.service";
import { AppThunkAction } from "../store";
import { RealEstateActionTypes } from "./real-estate.action.types";


export const getUserListings = (userId: number) : AppThunkAction<Promise<void>> => async (dispatch) : Promise<void> => {
  const filter: RealEstateFilter = {
    userIdList: [userId]
  }

  return RealEstateService.getAllRealEstate(0, 1000, filter)
    .then(response => response.data.data.content)
    .then(listings => {
      dispatch({
        type: RealEstateActionTypes.GET_USER_LISTINGS,
        payload: listings
      })
      return Promise.resolve()
    })
    .catch(err => {
      return Promise.reject()
    }) 
}

export const deleteUserListing = (listingId: number) : AppThunkAction<Promise<void>> => async (dispatch, getState) : Promise<void> => {

  return RealEstateService.deleteRealEstate(listingId)
    .then(response => response.data)
    .then(data => {

      console.log(data)
      dispatch({
        type: RealEstateActionTypes.DELETE_USER_LISTING,
        payload: listingId
      })
      return Promise.resolve()
    })
    .catch(err => {
      return Promise.reject()
    })
}