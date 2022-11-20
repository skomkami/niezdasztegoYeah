
import React from 'react'
import Quest from './Quest.js';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { textAlign } from '@mui/system';

export default function QuizComponent(props) {
  const [score, setScore] = React.useState(0);
  const [showAnswers, setShowAnswers] = React.useState(false);
  const [questions, setQuestions] = React.useState([]);
  const [allComplete, setAllComplete] = React.useState(false);
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;
  const [topic, setTopic] = React.useState("");

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
    fetch("https://opentdb.com/api.php?amount=5")
            .then(res => res.json())
            .then(data => setQuestions(data.results.map(function(question) {
                return({question:question.question,
                        options:question.incorrect_answers.concat([question.correct_answer]).map(value => ({ value, sort: Math.random() })).sort((a, b) => a.sort - b.sort).map(({ value }) => value),
                        selected_answer:undefined,
                        correct_answer:question.correct_answer})
            })))
            }
/*
    fetch(SERVER_URL + "?fraze=" + topic)
      .then((res) => res.json())
      .then((data) =>
        setQuestions(
          data.results.map(function (question) {
            return {
              question: question.question,
              options: question.incorrect_answers
                .concat([question.correct_answer])
                .map((value) => ({ value, sort: Math.random() }))
                .sort((a, b) => a.sort - b.sort)
                .map(({ value }) => value),
              selected_answer: undefined,
              correct_answer: question.correct_answer,
            };
          })
        )
      );
  }*/, [topic, SERVER_URL]);

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
        <div style={{position:"absolute", left:"200", right: "200", margin: "auto", text_align: "center"}}>
          <Typography variant="h4" textAlign="center">
              {props.topic}
          </Typography>
        </div>
      </div>
      {quests}
      <Button variant="contained">
        Add More Questions
      </Button>
      <Button variant="outlined">
        Export to PDF
      </Button>
    </Stack>
  );
}