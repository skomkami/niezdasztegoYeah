import React from 'react'
import QuestHeader from './QuestHeader.js';
import Paper from '@mui/material/Paper';
import { styled } from '@mui/material/styles';

const Item = styled(Paper)(({ theme }) => ({
    ...theme.typography.body2,
    color: theme.palette.text.secondary,
    //lineHeight: '60px',
    width: '90%',
    padding: '20px'
  }));

export default function Quest(props)
{
    
    function styler(option,index){
        if (props.showAnswers === true)
        {
            if(props.question.correct_answer === option)
            {
                return({backgroundColor: "#94D7A2"})
            }else if(props.question.selected_answer === index)
            {
                return({backgroundColor: "#F8BCBC"})
            }else{
                return({backgroundColor: "#F5F7FB"})
            }
        }else {
            return(props.question.selected_answer === index ? {backgroundColor: "#D6DBF5"} : {backgroundColor: "#F5F7FB"})
        }
    }
    

    
    const options = props.question.options.map((option,index) => <button
        key={index}
        dangerouslySetInnerHTML={{__html: option}}
        onClick={(event) => props.selectAnswer(event,props.id,index)}
        style={styler(option,index)}
        disabled={props.showAnswers}
        className='quiz-container-question-options-container-option'
        />)
        
    return(<Item elevation={6}>
        <QuestHeader question={props.question.question}/>
        <div className='quiz-container-question-options-container'>{options}</div>
    </Item>)
}