
import React, { useCallback } from 'react';
import Start from './components/Start.js';
import QuizComponent from './components/QuizComponent.js';
import blob from './images/blob.png';

export default function App()
{
    
    const [showStart, setShowStart] = React.useState(true)
    
    const wrappersetShowStart = useCallback(val => {
        setShowStart(val);
      }, [setShowStart]);
    
    function startQuiz()
    {      
        setShowStart(false)    
    }
    
    
    return(<div className='app'>
                {showStart ? <Start startQuiz={startQuiz}/> : 
                <QuizComponent showStartSetter={wrappersetShowStart}></QuizComponent> }
                <img className='blob1' src={blob} alt=''/>
                <img className='blob2' src={blob} alt=''/>
            </div>)
}