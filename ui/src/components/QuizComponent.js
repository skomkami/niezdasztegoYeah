
import React from 'react'
import Quest from './Quest.js';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import CircularProgress from '@mui/material/CircularProgress';

export default function QuizComponent(props) {
  const [score, setScore] = React.useState(0);
  const [showAnswers, setShowAnswers] = React.useState(false);
  const [questions, setQuestions] = React.useState([]);
  const [allComplete, setAllComplete] = React.useState(false);
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;
  const [topic, setTopic] = React.useState("");
  const [loading, setLoading] = React.useState(false);

  function playAgain() {
    props.showStartSetter(true);
    setTopic(props.topic);
    setShowAnswers(false);
    setAllComplete(false);
  }

  function checkAnswers() {
    setShowAnswers(true);
  }

  function selectAnswer(event, quest_id, option_id) {
    setQuestions(function (prev) {
      return questions.map(function (quest, qid) {
        if (quest_id === qid) {
          return { ...quest, selected_answer: option_id };
        } else {
          return quest;
        }
      });
    });
  }

  React.useEffect(() => {
    var count = 0;
    for (var i = 0; i < questions.length; i++) {
      if (typeof questions[i].selected_answer !== "undefined") {
        if (
          questions[i].options[questions[i].selected_answer] ===
          questions[i].correct_answer
        ) {
          count++;
        }
      }
    }
    setScore(count);
  }, [questions, showAnswers]);

  React.useEffect(() => {
      if(questions.length > 0) return;
      setLoading(true);
      /*fetch(SERVER_URL + '?' + new URLSearchParams({
          phrase: props.topic,
          size: 10,
      }))*/
      fetch('message.json')
      .then(res => res.json())
      .then(data => {
        console.log(data)
        setLoading(false);
        return setQuestions(data.questions.map(function(question) {
          return({question:question.question,
                  options:question.answers,
                  selected_answer:undefined,
                  correct_answer:question.correctAnswerIndex,
                  sourceUrl:question.sourceUrl})
      }))
    })
    })

  React.useEffect(() => {
    setAllComplete(
      questions.every((quest) => typeof quest.selected_answer !== "undefined")
    );
  }, [questions]);

  const quests = questions.map(function (question, index) {
    return (
      <Quest
        key={index}
        question={question}
        showAnswers={showAnswers}
        selectAnswer={selectAnswer}
        id={index}
      />
    );
  });

  return (
    <Stack 
                direction="column"
                justifyContent="center"
                alignItems="center"
                margin="20"
                spacing={3}>
      <div style={{position:"relative", width: "100%", height: "40px"}}>
        <div style={{position: "absolute", left: "0"}}>
          <Tooltip title="Return Back">
              <IconButton>
                  <ArrowBackIcon onClick={()=>{props.showStartSetter(true)}} />
              </IconButton>
          </Tooltip>
        </div>
        <div style={{position:"absolute", left:"100", right: "100", margin: "auto", text_align: "center"}}>
          <Stack>
          <Typography variant="p" textAlign="center">
            Test Topic:
          </Typography>
          <Typography variant="h4" textAlign="center">
            {props.topic}
          </Typography>
          </Stack>
        </div>
      </div>
      {loading ? <CircularProgress /> : 
      <Stack 
      direction="column"
      justifyContent="center"
      alignItems="center"
      margin="0"
      spacing={3}
      sx={{width:"100%"}}>
      {quests}
      <Button variant="contained">
        Add More Questions
      </Button>
      <Button variant="outlined">
        Export to PDF
      </Button></Stack>
      }
    </Stack>
  );
}