import { OpenStreetMapProvider, SearchControl } from "leaflet-geosearch";
import { useEffect } from "react"
import { useMap } from "react-leaflet"
import { useAppThunkDispatch } from "../../state/store";
import { selectLocation } from '../../state/leaflet-map/leaftlet-map.action.creators'
import { LatLng } from "leaflet";

const LeafletGeoSearch = () => {
  const map = useMap()
  const dispatch = useAppThunkDispatch()

  useEffect(() => {
    const provider = new OpenStreetMapProvider()
    const searchControl = SearchControl({
      provider,
      
    })

    map.on('geosearch/showlocation', (e: any) => {
      dispatch(selectLocation(new LatLng(e.location.x, e.location.y)))
    })

    map.addControl(searchControl)
  }, [])

  return null
}

export default LeafletGeoSearch