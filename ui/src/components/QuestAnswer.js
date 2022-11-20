import React from 'react'
import TextField from '@mui/material/TextField';


export default function QuestAnswer(props)
{
    
    function styler(option,index){
        if(props.question.correct_answer === index)
        {
            return({backgroundColor: "#94D7A2"})
        }else{
            return({backgroundColor: "#F5F7FB"})
        }
    }
        
    return(<button
            key={props.index}
            style={styler(props.option,props.index)}
            disabled={props.showAnswers}
            className='quiz-container-question-options-container-option'
            >
                <TextField multiline id="standard-basic" 
                    label="" 
                    variant="standard" 
                    defaultValue={props.option}
                    sx={{width: "100%"}}/>
        </button>)
}