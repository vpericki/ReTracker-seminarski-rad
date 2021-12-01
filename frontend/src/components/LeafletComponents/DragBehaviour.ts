import { useMapEvents } from "react-leaflet"
import { useAppThunkDispatch } from '../../state/store'
import { LatLngBounds } from 'leaflet'
import { saveBounds } from '../../state/leaflet-map/leaftlet-map.action.creators'

const DragComponent = () => {
  const dispatch = useAppThunkDispatch()

  useMapEvents({
    dragend: (e) => {
      dispatch(saveBounds(e.target.getBounds() as LatLngBounds)).then(() => {

      })
    },
    zoomend: (e) => {
      dispatch(saveBounds(e.target.getBounds() as LatLngBounds)).then(() => {
        
      })
    }
})

  return null
}

export default DragComponent