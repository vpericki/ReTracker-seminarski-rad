import { AxiosResponse, AxiosResponseHeaders } from "axios"
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router"
import { RealEstateService } from "../../services/realestate.service"
import { RealEstate } from "../../interfaces/RealEstate"
import { Card, Rating, Typography } from "@mui/material"
import { useSelector } from "react-redux";
import { RootState } from "../../state/store"
import { UserState } from "../../state/auth/auth.reducer"


const RealEstateDetails = () => {
  const params = useParams()
  const navigate = useNavigate()

  const { isLoggedIn } = useSelector<RootState, UserState>((state: RootState) => state.auth)

  const [realEstate, setRealEstate] = useState<RealEstate>()
  const [urls, setUrls] = useState<{ url: string}[]>([])

  useEffect(() => {
    const id = parseInt(params.id as string)

    RealEstateService.getRealEstateById(id)
      .then(response => response.data.data)
      .then(data => {
        setRealEstate(data)
        console.log(data)

        let imageUrls: {url:string}[] = [] 
        data.images.forEach(image => imageUrls.push({url: image.path}))

        setUrls(imageUrls)
      }) 
      .catch((err: AxiosResponse) => {
        if (err.status === 401) navigate('/forbidden')
      })


  }, [])

  return (
    <div className="w-full flex flex-col p-4">
    <Card className="w-max-md flex flex-col self-center">
      <div className="flex flex-col self-center gap-2 p-4">
        {realEstate?.images.map(img => (
          <img key={img.id} src={`${img.path}`} />
        ))}
      </div>

      <div className="flex flex-row p-4 gap-6 justify-between">
        <div className="flex flex-col gap-6">
          <div>
          <Typography variant="h6">{realEstate?.name}</Typography>
          <p>{realEstate?.description}</p>
          </div>
          
          <div>
            <Typography variant="h6" className="mt-4">Details</Typography>
            <p>Quadrature: {realEstate?.quadrature}</p>
            <p>Number of rooms: {realEstate?.rooms}</p>
            <p>Number of bathrooms: {realEstate?.baths}</p>
            <p>Type: {realEstate?.realEstateTypeDto.type}</p>
          </div>

          <div>
            <Typography variant="h6" className="mt-4">Contact</Typography>
            <p>Email: {realEstate?.createdBy?.email}</p>

          </div>
        </div>

        <div className="flex flex-col justify-between">
          <Rating
            value={realEstate?.rating || 0}
            onChange={(event, newValue) => {
              RealEstateService.updateRealEstateRating(realEstate?.id as number, newValue!)
                .then(response => response.data.data)
                .then(data => {
                  setRealEstate(data)
                })
                .catch(err => {
                  console.log(err)
                })
            }}
            readOnly={!isLoggedIn}
            precision={0.25}
            className="self-center md:self-end"
          />

          <div className="flex flex-col self-end">
            <p className="font-bold">{realEstate?.forRent ? 'For rent' : 'For sale'}</p>
            <p className="text-right">{realEstate?.forRent ? `${realEstate.rentPrice}` : `${realEstate?.sellPrice}`}</p>

          </div>
        </div>
       
      </div>

    </Card>
    </div>
   
    
  )
}

export default RealEstateDetails