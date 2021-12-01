import { Button, Card, FormControlLabel, Input, Switch, TextField } from "@mui/material"
import { useState } from "react"
import { RealEstateFilter } from "../../interfaces/RealEstate/RealEstateFilter"
import { useAppThunkDispatch } from "../../state/store"
import { findFromFilter, toggleShowOnlyFromFilter } from '../../state/leaflet-map/leaftlet-map.action.creators'
import { RealEstate } from "../../interfaces/RealEstate"

const AdvancedREFilter = () => {

  const dispatch = useAppThunkDispatch()

  const [filter, setFilter] = useState<RealEstateFilter>()
  const [toggle, setToggle] = useState('off')

  const handleFilterClick = () => {
    dispatch(findFromFilter(filter as RealEstate))
      .then(() => {

      })
      .catch(err => {

      })
  }

  const toggleMapLoading = (e: any) => {
    const targetValue = e.target.value
    setToggle(targetValue)

    if (targetValue === 'on') {
      dispatch(toggleShowOnlyFromFilter(true))
    }
    else {
      dispatch(toggleShowOnlyFromFilter(false))
    }
  }

  //<FormControlLabel className="self-center" control={<Switch value={toggle} onClick={toggleMapLoading} />} label="Show only from filter" />
  return (
    <div className="flex flex-col gap-2">
      <TextField label="Name" variant="filled"  onChange={(e) => setFilter({...filter, name: e.target.value})} />
      <TextField label="Description" variant="filled"  onChange={(e) => setFilter({...filter, description: e.target.value})} />
      <TextField label="Min quadrature" type="number" variant="filled"  onChange={(e) => setFilter({...filter, minQuadrature: parseInt(e.target.value)})} />
      <TextField label="Max quadrature" type="number" variant="filled"  onChange={(e) => setFilter({...filter, maxQuadrature: parseInt(e.target.value)})} />
      <TextField label="Min rating" type="number" variant="filled"  onChange={(e) => setFilter({...filter, minRating: parseFloat(e.target.value)})} />
      <TextField label="Max rating" type="number" variant="filled"  onChange={(e) => setFilter({...filter, maxQuadrature: parseFloat(e.target.value)})} />
      <TextField label="Min rooms" type="number" variant="filled"  onChange={(e) => setFilter({...filter, minRooms: parseFloat(e.target.value)})} />
      <TextField label="Max rooms" type="number" variant="filled"  onChange={(e) => setFilter({...filter, maxRooms: parseFloat(e.target.value)})} />
      <TextField label="Min baths" type="number" variant="filled"  onChange={(e) => setFilter({...filter, minBaths: parseFloat(e.target.value)})} />
      <TextField label="Max baths" type="number" variant="filled"  onChange={(e) => setFilter({...filter, maxBaths: parseFloat(e.target.value)})} />


      <Button variant={"contained"} onClick={handleFilterClick}>Filter RE</Button>
    </div>
  )
}

export default AdvancedREFilter