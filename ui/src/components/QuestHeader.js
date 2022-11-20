import React from 'react'
import DeleteIcon from '@mui/icons-material/Delete';
import RefreshIcon from '@mui/icons-material/Refresh';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import LinkIcon from '@mui/icons-material/Link';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';

export default function QuestHeader(props)
{
    const [setQuestion] = React.useState("");

        
    return(<div style={{'padding-left': '10px', 'padding-right': '10px'}}>
        <Grid container spacing={2}>
            <Grid item xs={10.5}>
            <TextField multiline  id="standard-basic" 
                label="" 
                variant="standard" 
                defaultValue={props.question} 
                onChange={(e)=>{setQuestion(e.target.value)}}
                inputProps={{style: {fontWeight: "bold"}}}
                sx={{width: "100%"}}/>
            </Grid>
            <Grid item xs={0.5}>
                <Tooltip title="View Source">
                    <IconButton>
                        <LinkIcon />
                    </IconButton>
                </Tooltip>
            </Grid>
            <Grid item xs={0.5}>
                <Tooltip title="Regenerate">
                    <IconButton>
                        <RefreshIcon />
                    </IconButton>
                </Tooltip>
            </Grid>
            <Grid item xs={0.5}>
                <Tooltip title="Delete" color="error">
                    <IconButton>
                        <DeleteIcon />
                    </IconButton>
                </Tooltip>
            </Grid>
        </Grid>
        </div>
    )
}