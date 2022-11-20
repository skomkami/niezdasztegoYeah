import React from "react";
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';

export default function Start(props) {
  const [topic, setTopic] = React.useState("");

  function handleChange({ target }) {
    setTopic(target.value);
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
      <TextField id="topicBox" label="Enter topic here..." variant="outlined"
        value={topic}
        onChange={handleChange}
      />
      <Button
        variant="contained"
        onClick={() => {props.startQuiz(topic)}}
      >
        Start Quiz
      </Button>
    </Stack>
    </div>
  );
}
