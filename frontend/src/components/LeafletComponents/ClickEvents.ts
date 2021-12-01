import { useMapEvents } from "react-leaflet"
import { useAppThunkDispatch } from "../../state/store";
import { selectLocation } from '../../state/leaflet-map/leaftlet-map.action.creators'
import { LatLng } from "leaflet";

const ClickEvents = () => {
  const dispatch = useAppThunkDispatch()

  useMapEvents({
    click: (e) => {
      dispatch(selectLocation(new LatLng(e.latlng.lat, e.latlng.lng)))
    }
  })

  return null
}

export default ClickEvents