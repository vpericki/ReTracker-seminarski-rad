import { SchemaOf, object, string, date, ref } from 'yup'
import { useForm } from "react-hook-form";
import { UserRegister } from '../interfaces/UserRegister';
import { yupResolver } from '@hookform/resolvers/yup'
import { Button, TextField } from '@mui/material';
import DatePicker from '@mui/lab/DatePicker'
import { useState } from 'react';
import { LocalizationProvider } from '@mui/lab';
import AdapterDateFns from '@mui/lab/AdapterDateFns'
import { RootState, useAppThunkDispatch } from '../state/store';
import { register as registerFromState } from '../state/auth/auth.action.creators'
import { useSelector } from 'react-redux';
import { MessageState } from '../state/message/message.reducer';
import { useNavigate } from 'react-router';

const RegisterScreen = () => {
  const dispatch = useAppThunkDispatch()
  let navigate = useNavigate()

  const [dateOfBirth, setDateOfBirth] = useState<Date | null>(null)
  const { message } = useSelector<RootState, MessageState >((state: RootState) => state.message)

  const registerSchema: SchemaOf<UserRegister> = object({
    username: string()
      .defined()
      .required('Username is required')
      .min(5, 'Username must be at least 5 characters long')
      .max(20),
    password: string()
      .defined()
      .required('Password is required')
      .min(8, 'Password must be at least 8 characters long')
      .max(30),
    repeatPassword: string()
      .oneOf([ref('password'), null], 'Passwords must match')
      .defined()
      .required(),
    dateOfBirth: date()
      .defined()
      .nullable()
      .required()
      .typeError('Please enter the date of birth')
      .default(undefined),
    email: string()
      .email()
      .defined()
      .required('Email is required'),
    firstName: string()
      .defined()
      .required('First name is required')
      .max(30),
    lastName: string()
      .defined()
      .required('Last name is required')
      .max(30)
  })

  const {
    register,
    handleSubmit,
    formState: {errors}
  } = useForm<UserRegister>({
    resolver: yupResolver(registerSchema)
  })
  
  const onSubmit = (data: UserRegister) => {
    dispatch(registerFromState(data))
      .then(data => {
        navigate('/login', { state: data })
      })
      .catch(() => {})
  }

  return (
    <div className="flex flex-col">
      <form onSubmit={handleSubmit(onSubmit)} className="max-w-lg self-center gap-2 flex flex-col"> 
          <h1 className="text-2xl">Register</h1>
          <div className="flex flex-col gap-3">
            <TextField id="username" label="Username" variant="filled" { ...register('username') } error={!!errors.username} helperText={errors.username?.message} />
            <TextField id="email" label="Email" type="email" variant="filled" {...register('email')} error={!!errors.email} helperText={errors.email?.message} />
            <TextField id="password" label="Password" type="password" variant="filled" {...register('password')} error={!!errors.password} helperText={errors.password?.message} />
            <TextField id="repeatPassword" label="Repeat password" type="password" variant="filled" {...register('repeatPassword')} error={!!errors.repeatPassword} helperText={errors.repeatPassword?.message} />
          </div>

          <div className="flex gap-2">
            <TextField id="firstName" label="First name" variant="filled" {...register('firstName')} error={!!errors.firstName} helperText={errors.firstName?.message} />
            <TextField id="lastName" label="Last name" variant="filled" {...register('lastName')} error={!!errors.lastName} helperText={errors.lastName?.message} />
          </div>

          <div className="mt-1">
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DatePicker value={dateOfBirth} onChange={(newValue) => { setDateOfBirth(newValue) }} label="Date of birth" renderInput={(params) => <TextField type="date" className="w-full" id="dateOfBirth" {...params} { ...register('dateOfBirth') } error={!!errors.dateOfBirth} helperText={errors.dateOfBirth?.message}  />} />
            </LocalizationProvider>
          </div>

          <div className="self-end">
            <Button variant="contained" type="submit">Register</Button>
          </div>
      </form>

      {message && (
        <div className="self-center text-red-800">{message}</div>
      )}
    </div>
  )
}

export default RegisterScreen;