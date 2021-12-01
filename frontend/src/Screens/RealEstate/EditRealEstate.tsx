import { array, boolean, mixed, number, object, SchemaOf, string,  } from "yup"
import { RealEstateAddressPost } from '../../interfaces/Post/RealEstateAddressPost'
import { FormControl, InputLabel, MenuItem, Select, TextField, SelectChangeEvent, Button, Card, CardContent, Typography, RadioGroup, FormControlLabel, Radio } from "@mui/material"
import { useForm } from "react-hook-form"
import { yupResolver } from "@hookform/resolvers/yup"
import { RealEstatePost } from "../../interfaces/Post/RealEstatePost"
import { useState } from "react"
import { MapContainer, Marker, TileLayer } from "react-leaflet"
import { LatLng } from "leaflet"
import LeafletGeoSearch from "../../components/LeafletComponents/LeafletGeoSearch"
import ClickEvents from "../../components/LeafletComponents/ClickEvents"
import { RootState } from "../../state/store"
import { MapState } from "../../state/leaflet-map/leaflet-map.action.reducer"
import { UserState } from "../../state/auth/auth.reducer"
import { useSelector } from "react-redux"
import { RealEstateValidation } from "../../interfaces/Post/RealEstateValidation"
import { storage } from "../../firebase"
import { getDownloadURL, ref, uploadBytes, UploadResult } from 'firebase/storage'
import uid from "../../Extensions"
import { RealEstateService } from "../../services/realestate.service"
import { RealEstateImagePost } from "../../interfaces/Post/RealEstateImagePost"
const uuid = require('uuid')

const EditRealEstate = () => {

  const mapCenter = new LatLng(52.505, -0.09)
  const { latLng } = useSelector<RootState, MapState>((state: RootState) => state.map)
  const { user } = useSelector<RootState, UserState>((state: RootState) => state.auth)

  interface RealEstateType {
    id: number,
    type: string
  }

  const realEstateTypes: RealEstateType[] = [
    { id: 1, type: 'LOT'},
    { id: 2, type: 'APARTMENT' },
    { id: 3, type: 'HOUSE'}
  ]

  const [typeValue, setTypeValue] = useState({
    id: realEstateTypes[0].id,
    type: realEstateTypes[0].type
  })

  const [purchaseType, setPurchaseType] = useState('rent')
  const [marker, setMarker] = useState({})

  const handleRealEstateTypeChange = (event: SelectChangeEvent) => {
    const reType = realEstateTypes.find((type) => type.type === event.target.value)!
    
    if (reType) {
      setTypeValue({
        id: reType.id,
        type: reType.type
      })
    }
    setValue('realEstateTypeDto', { id: reType.id!, type: reType.type! })
  }

  const changePurchaseType = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPurchaseType((event.target as HTMLInputElement).value)
  }

  const realEstateTypeSchema: SchemaOf<RealEstateType> = object().shape({
    id: number().required(),
    type: string().required()
  })

  const realEstateAddressSchema: SchemaOf<RealEstateAddressPost> = object().shape({
    city: string().required().defined(),
    country: string().required().defined(),
    houseNumber: string().required().defined(),
    latitude: number().required().defined().typeError('You must specify a number'),
    longitude: number().required().defined().typeError('You must specify a number'),
    state: string().required().defined(),
    doorNumber: string().default(null),
    floor: string().default(null),
    street: string().required().defined(),
  })

  const realEstateSchema: SchemaOf<RealEstateValidation> = object({
    images: mixed().defined(),
    forRent: boolean().defined().default(true).required(),
    forSale: boolean().defined().default(false),
    rentPrice: number().defined().typeError('You must specify a number'),
    sellPrice: number().defined().typeError('You must specify a number'),
    baths: number().defined().typeError('You must specify a number'),
    rooms: number().defined().typeError('You must specify a number'),
    quadrature: number().required().defined().typeError('You must specify a number'),
    description: string().required().defined(),
    name: string().required().defined(),
    realEstateTypeDto: realEstateTypeSchema.required().default(realEstateTypes[0]),
    address: realEstateAddressSchema.required()
  })

  const {
    register,
    handleSubmit,
    formState: {errors},
    setValue,
    getValues
  } = useForm<RealEstateValidation>({
    resolver: yupResolver(realEstateSchema)
  })

  const onSubmit = async (data: RealEstateValidation) => {

    const files = Array.from(data.images as File[])
    const filePaths: RealEstateImagePost[] = []

    const uploadPromises: Promise<UploadResult | void | number>[] = []
    files.forEach(file => {
      const uniqueFileName = uid.generate()
      let folderName = uid.generate()
      if (user) {
        folderName = user.id.toString()
      }

      const storageRef = ref(storage, `images/${folderName}/${uniqueFileName}`)
      uploadPromises.push(uploadBytes(storageRef, file).then((snapshot) => {
        const pathRef = ref(storage, snapshot.metadata.fullPath)
        return getDownloadURL(pathRef)
      })
      .then((url: string) => filePaths.push({path: url}))) 
    })

    Promise.all(uploadPromises)
      .then(() => {
        console.log("uplaoded files")

        data = {
          ...data,
          images: filePaths as RealEstateImagePost[],
        }
    
        const postData = data as RealEstatePost
    
        RealEstateService.create(postData)
          .then(data => {
            console.log(data)
          })
          .catch(err => {
            console.log(err!.response)
          })
      })
  }

  return (
    <div className="flex flex-col  md:flex-row justify-center">
      <Card className="w-1/4 m-4">
        <CardContent className="flex flex-col gap-2">

          <Typography variant="h5">
              Create a new real estate listing
          </Typography>

          <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-3">
            <TextField id="name" label="Name" variant="filled" { ...register('name') } error={!!errors.name} helperText={errors.name?.message} />
            <TextField id="description" label="Description" variant="filled" { ...register('description') } error={!!errors.description} helperText={errors.description?.message} />
            <TextField id="quadrature" label="Quadrature" type="number" variant="filled" { ...register('quadrature') } error={!!errors.quadrature} helperText={errors.quadrature?.message} />
            <TextField id="rooms" label="Rooms" type="number" variant="filled" { ...register('rooms') } error={!!errors.rooms} helperText={errors.rooms?.message} />
            <TextField id="baths" label="Baths" type="number" variant="filled" { ...register('baths') } error={!!errors.baths} helperText={errors.baths?.message} />
            <TextField id="rentPrice" label="Rent price" type="number" variant="filled" { ...register('rentPrice') } error={!!errors.rentPrice} helperText={errors.rentPrice?.message} />
            <TextField id="sellPrice" label="Sell price" type="number" variant="filled" { ...register('sellPrice') } error={!!errors.sellPrice} helperText={errors.sellPrice?.message} />

            <RadioGroup
              aria-label="gender"
              name="controlled-radio-buttons-group"
              className="flex flex-row"
              value={purchaseType}
              onChange={changePurchaseType}
            >
              <FormControlLabel value="rent" control={<Radio />} onClick={() => { setValue('forRent', true); setValue('forSale', false) }} label="For rent" />
              <FormControlLabel value="sale" control={<Radio />} onClick={() => { setValue('forRent', false); setValue('forSale', true)}} label="For sale" />
            </RadioGroup>

            <FormControl variant="filled" error={!!errors.realEstateTypeDto}>
              <InputLabel id="realEstateTypeLabel">Real estate type</InputLabel>
              <Select id="realEstateTypeDto" defaultValue="APARTMENT" labelId="realEstateTypeLabel" value={typeValue.type} onChange={handleRealEstateTypeChange} type="number" variant="filled" >
                <MenuItem value={"LOT"}>Lot</MenuItem>
                <MenuItem value={"APARTMENT"}>Apartment</MenuItem>
                <MenuItem value={"HOUSE"}>House</MenuItem>
              </Select>
            </FormControl>

            <TextField id="country" label="Country" variant="filled" { ...register('address.country') } error={!!errors.address?.country} helperText={errors.address?.country?.message} />
            <TextField id="state" label="State" variant="filled" { ...register('address.state') } error={!!errors.address?.state} helperText={errors.address?.state?.message} />
            <TextField id="city" label="City" variant="filled" { ...register('address.city') } error={!!errors.address?.city} helperText={errors.address?.city?.message} />
            <TextField id="street" label="Street" variant="filled" { ...register('address.street') } error={!!errors.address?.street} helperText={errors.address?.street?.message} />
            <TextField id="houseNumber" label="House number" variant="filled" { ...register('address.houseNumber') } error={!!errors.address?.houseNumber} helperText={errors.address?.houseNumber?.message} />
            <TextField id="floor" label="Floor" variant="filled" { ...register('address.floor') } error={!!errors.address?.floor} helperText={errors.address?.floor?.message} />
            <TextField id="doorNumber" label="Door number" variant="filled" { ...register('address.doorNumber') } error={!!errors.address?.doorNumber} helperText={errors.address?.doorNumber?.message} />
            <TextField id="longitude" type="number" label="Longitude" value={(latLng ? latLng.lng as number: '')} variant="filled" { ...register('address.longitude') } error={!!errors.address?.longitude} helperText={errors.address?.longitude?.message} />
            <TextField id="latitude" type="number" label="Latitude" value={(latLng? latLng.lat as number : '')} variant="filled" { ...register('address.latitude') } error={!!errors.address?.latitude} helperText={errors.address?.latitude?.message} />
            
            <div className="flex flex-col">
              <Typography variant="h6">
                Property images
              </Typography>

              <input
                accept="image/*"
                multiple
                type="file"
                {...register('images')}
              />
              <span>{!!errors.images}</span>
            </div>

            <Button type="submit" variant="contained">Create</Button>
          </form>
        </CardContent>
      </Card>
      
      <div className="w-3/4 ">
        <MapContainer zoom={13} center={latLng ? latLng : mapCenter}>
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          <LeafletGeoSearch />
          <ClickEvents />

          {latLng ? (
            <Marker position={latLng}/>
          ) : ''}
        </MapContainer>
      </div>
    </div>
  )
}

export default EditRealEstate