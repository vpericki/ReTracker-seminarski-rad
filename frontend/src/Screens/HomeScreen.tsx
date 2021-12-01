import { LatLng } from "leaflet";
import { useEffect, useState } from "react";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";
import DragBehaviour from '../components/LeafletComponents/DragBehaviour'
import MarkerClusterGroup from 'react-leaflet-markercluster'
import { Card, CardContent, Container, Typography } from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../state/store";
import { MapState } from "../state/leaflet-map/leaflet-map.action.reducer";
import { RealEstateTypes } from "../enums/RealEstateTypes";
import GrassIcon from '@mui/icons-material/Grass';
import RealEstateCard from "../components/RealEstate/RealEstateCard";
import AdvancedREFilter from "../components/RealEstate/AdvancedREFilter";
import { Link } from "react-router-dom";
 
const HomeScreen = () => {

  const { listing, showOnlyFromFilter } = useSelector<RootState, MapState>((state: RootState) => state.map)
  const center: LatLng = new LatLng(45.765, 15.993)

  console.log(listing)

  useEffect(() => {

  }, [])

  return (
      <div className="flex flex-col w-full justify-center p-4 gap-3" style={{height: '100%'}}>
        <div className="w-full md:w-6/12 flex flex-col md:flex-row self-center gap-3">
          <section className="w-full self-center" style={{height: '70vh'}}>
            <MapContainer center={center} zoom={13} className="markercluster-map w-full" >
              <TileLayer
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              />
              <MarkerClusterGroup>
                {listing.map((realEstate, id) => (
                      <Marker key={id} position={new LatLng(realEstate.address.latitude, realEstate.address.longitude)}>
                        <Popup>
                          <Typography variant="h5"><Link to={`real-estate/${realEstate.id}`}>{realEstate.name}</Link></Typography>
                          {realEstate.forRent ? <Typography>For rent</Typography> : <Typography>For sale</Typography>}
                        </Popup>
                      </Marker>
                    )
                  )}
              </MarkerClusterGroup>
          
              <DragBehaviour />
            </MapContainer>
          </section>
          <section className="">
            <AdvancedREFilter />
          </section>
          
        </div>
        <section className="w-full md:w-6/12 self-center flex flex-col gap-3">
          {listing.map(re => <RealEstateCard key={re.id} {...re} />)}

        </section>
        
      </div>
      
      
  )
}

export default HomeScreen;