import { Button, TextField } from "@mui/material";
import { Box } from "@mui/system";
import { SyntheticEvent, useState } from "react";
import { useSelector } from "react-redux";
import {  useNavigate } from "react-router";
import { login } from "../state/auth/auth.action.creators";
import { MessageState } from "../state/message/message.reducer";
import { RootState, useAppThunkDispatch } from "../state/store";

const LoginScreen = () => {
  const navigate = useNavigate()
  const dispatch = useAppThunkDispatch()

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const { message } = useSelector<RootState, MessageState >((state: RootState) => state.message)

  const submitHandler = async (e: SyntheticEvent) => {
    e.preventDefault()

    dispatch(login(username, password))
      .then(() => {
        navigate('/')
      })
      .catch (() => {})
  }

  return (
    <Box display="flex" justifyContent="center" alignItems="center" padding="3em">
        <form onSubmit={submitHandler} className="w-full md:w-1/3">
          <div className="flex flex-col gap-2">
            <TextField id="username" label="Username" variant="filled" onChange={(e) => setUsername(e.target.value)} />
            <TextField id="password" label="Password" variant="filled" onChange={(e) => setPassword(e.target.value)} type="password"/>
            
            <Button variant="contained" type="submit">Login</Button>
            {message && (
              <div className="self-center text-red-800">{message}</div>
            )}
          </div>
        </form>



    </Box>
  )
}

export default LoginScreen;