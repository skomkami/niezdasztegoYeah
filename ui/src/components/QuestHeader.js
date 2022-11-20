import React from 'react'
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import RefreshIcon from '@mui/icons-material/Refresh';
import Tooltip from '@mui/material/Tooltip';
import Grid from '@mui/material/Grid';

export default function QuestHeader(props)
{
        
    return(<Grid container spacing={2}>
            <Grid item xs={7}>
                <h1 className='quiz-container-question-title' dangerouslySetInnerHTML={{__html: props.question}}/>
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
    )
}