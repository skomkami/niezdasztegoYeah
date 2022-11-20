import React from 'react'

export default function QuestHeader(props)
{
        
    return(<div className='quiz-container-question-header'>
        <h1 className='quiz-container-question-title' dangerouslySetInnerHTML={{__html: props.question}}/>
        </div>
    )
}