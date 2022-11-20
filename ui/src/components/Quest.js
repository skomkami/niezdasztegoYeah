import React from 'react'
import QuestHeader from './QuestHeader.js';
import Paper from '@mui/material/Paper';
import { styled } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import QuestAnswer from './QuestAnswer.js';

const Item = styled(Paper)(({ theme }) => ({
    ...theme.typography.body2,
    color: theme.palette.text.secondary,
    //lineHeight: '60px',
    width: '90%',
    padding: '20px'
  }));

export default function Quest(props)
{
    

    
    const options = props.question.options.map((option,index) => <QuestAnswer question={props.question} option={option} index={index} />)
        
    return(<Item elevation={6}>
        <QuestHeader question={props.question.question} sourceUrl={props.question.sourceUrl}/>
        <div className='quiz-container-question-options-container'>{options}</div>
    </Item>)
}