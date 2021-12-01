import { Card, CardContent, Typography } from "@mui/material"

const Forbidden = () => {

  return (
    <div className="w-full mt-5 flex flex-col">
      <div className="max-w-md self-center">
      <Card >
        <CardContent>
          <Typography variant="h6">401</Typography>
          You do not have access to use this resource.
        </CardContent>
      </Card>
      </div>
    </div>
  
  )
}

export default Forbidden