import React from "react";
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';

export default function Start(props) {
  const [topic, setTopic] = React.useState("");
  const [isEmpty, setIsEmpty] = React.useState(false);

  function handleChange({ target }) {
    setTopic(target.value);
  }

  function startQuiz(topic) {
    if(topic.trim().length <= 0){
      setIsEmpty(true);
      return;
    }
    props.startQuiz(topic)
  }

  return (<div class="start-container">
    <Stack 
      direction="column"
      justifyContent="center"
      alignItems="center"
      margin="20"
      spacing={3}>
      <h1 className="start-container-title">Quizzical</h1>
      <h2 className="start-container-subtitle">Query Your Brain</h2>
      <TextField error={isEmpty} id="topicBox" label="Enter topic here..." variant="outlined"
        value={topic}
        onChange={handleChange}
      />
      <Button
        variant="contained"
        onClick={() => {startQuiz(topic)}}
      >
        Start Quiz
      </Button>
    </Stack>
    </div>
  );
}
