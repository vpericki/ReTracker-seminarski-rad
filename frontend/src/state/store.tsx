import { useDispatch } from "react-redux";
import { Action, AnyAction, applyMiddleware, combineReducers, createStore } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import thunk, { ThunkAction, ThunkDispatch } from 'redux-thunk'
import { authReducer } from "./auth/auth.reducer";
import { LeaftletMapReducer } from "./leaflet-map/leaflet-map.action.reducer";
import { messageReducer } from "./message/message.reducer";
import { RealEstateReducer } from "./real-estate/real-estate.action.reducer";

const reducers = combineReducers({
  auth: authReducer,
  message: messageReducer,
  map: LeaftletMapReducer,
  realEstates: RealEstateReducer
})

const middleWare = [thunk]

const store = createStore(
  reducers,
  composeWithDevTools(applyMiddleware(...middleWare))
)

export default store
export type RootState = ReturnType<typeof store.getState>

export type ThunkAppDispatch = ThunkDispatch<RootState, void, Action>
export const useAppThunkDispatch = () => useDispatch<ThunkAppDispatch>()

export type AppThunkAction<ReturnType = void> = ThunkAction<ReturnType, RootState, unknown, AnyAction>