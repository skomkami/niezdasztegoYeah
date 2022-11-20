import React from 'react'
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';

export default function TopBar(props)
{
        
    return(
        <AppBar position="static"><Toolbar>
            <Typography onClick={() => {props.showStartSetter(true)}} variant="h4" component="div" sx={{ flexGrow: 1, fontWeight: 'bold', cursor: 'pointer' }}>
                Quizzical
            </Typography>
            <Button variant="contained" color="white" sx={{width: "100", color: "#B71B1B", margin: "10", fontWeight: "bold"}}>Login</Button>
            <Button variant="outlined" color="white" sx={{width: "100", margin: "10", fontWeight: "bold"}}>Sign in</Button>
          </Toolbar>
        </AppBar>
    )
}