import { Card, Typography } from "@mui/material"
import Rating from "@mui/material/Rating"
import { RealEstate } from "../interfaces/RealEstate"

const TestCard = (realEstate: RealEstate) => {

    return (
            <Card>
                <div className="flex flex-col md:flex-row justify-between">
                    <section className="flex flex-col md:flex-row text-center md:text-left gap-4">
                        <img src={realEstate.images[0].path} className="w-56 self-center md:self-start" />
                        
                        <div className="flex flex-col justify-between p-4">
                            <div>
                                <Typography variant="h4">{realEstate.name}</Typography>
                                <p>{realEstate.description}</p>
                            </div>
                            <div>
                                <p className="font-medium">{realEstate.createdBy?.username || ''}</p>
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
            </Card>
    )
}

export default TestCard