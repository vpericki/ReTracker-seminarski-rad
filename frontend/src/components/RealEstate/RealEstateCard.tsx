import { Card, CardActions, IconButton, Rating, Typography } from "@mui/material"
import { useSelector } from 'react-redux'
import { RealEstate } from '../../interfaces/RealEstate'
import { UserState } from '../../state/auth/auth.reducer'
import { RootState } from '../../state/store'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit';
import { useAppThunkDispatch } from '../../state/store'
import { deleteUserListing } from "../../state/real-estate/real-estate.action.creators"
import { Link } from "react-router-dom"


const RealEstateCard = (realEstate: RealEstate) => {

  const dispatch = useAppThunkDispatch()
  const { user } = useSelector<RootState, UserState>((state: RootState) => state.auth)

  const deleteListing = (listingId: number) => {
    dispatch(deleteUserListing(listingId))
      .then(() => {
        console.log("Deleted user listing")
      })
      .catch(err => {
        console.log("Error deleting user listing " + err)
      })
  }

  return (
    <Card>
            <div className="flex flex-col md:flex-row justify-between">
                <section className="flex flex-col md:flex-row text-center md:text-left gap-4">
                    <img src={realEstate.images[0]?.path} alt="house image" className="md:w-56 self-center md:self-start" />
                    
                    <div className="flex flex-col justify-between p-4">
                        <div>
                            <Typography variant="h4"><Link to={`/real-estate/${realEstate.id}`}>{realEstate.name}</Link> </Typography>
                            <p>{realEstate.description}</p>
                        </div>
                        <div>
                            <p className="font-medium"><Link to={`/user/${realEstate.createdBy?.id}`}>{realEstate.createdBy?.username || ''}</Link></p>
                        </div>
                    </div>
                </section>

                <section className="flex flex-col justify-between p-4 gap-2">
                    <div className="flex flex-col text-center md:text-right">
                        <Typography>{realEstate.forRent ? 'For rent' : 'For sale'}</Typography>
                        <p className="font-bold">{realEstate.sellPrice ? `HRK ${realEstate.sellPrice.toLocaleString()}` : `HRK ${realEstate.rentPrice?.toLocaleString()}`}</p>
                    </div>

                    <div className="flex flex-col gap-2 text-center md:text-right">
                    <Rating
                        value={realEstate.rating || 0}
                        name="read-only"
                        readOnly
                        precision={0.25}
                        className="self-center md:self-end"
                    />
                    </div>
                </section>
            </div>

            {realEstate.createdBy?.id === user?.id ? (
              <CardActions className="flex flex-row justify-end border-t-2 border-gray-100">
                <IconButton><EditIcon /></IconButton>
                <IconButton onClick={() => {
                  deleteListing(realEstate.id!)
                }}><DeleteIcon className="text-red-600" /></IconButton>
              </CardActions>
            ) : ''}
           
      </Card>
  )
}

export default RealEstateCard