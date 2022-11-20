import React, { useCallback } from "react";
import Start from "./components/Start.js";
import QuizComponent from "./components/QuizComponent.js";
import { ThemeProvider, createTheme } from '@mui/material/styles';
import TopBar from "./components/TopBar.js";

const theme = createTheme({
    palette: {
        primary: {
        main: "#B71B1B",
        },
        white: {
            main: "#ffffff"
        }
    },
});

export default function App() {
  const [showStart, setShowStart] = React.useState(true);
  const [topic, setTopic] = React.useState("");

  const wrappersetShowStart = useCallback(
    (val) => {
      setShowStart(val);
    },
    [setShowStart]
  );

  function startQuiz(quizTopic) {
    console.log(quizTopic);
    setTopic(quizTopic);
    setShowStart(false);
  }

  return (
    <ThemeProvider theme={theme}>
        <TopBar showStartSetter={wrappersetShowStart}/>
        <div className="app">
        {showStart ? (
            <Start startQuiz={startQuiz} />
        ) : (
            <QuizComponent
            showStartSetter={wrappersetShowStart}
            topic={topic}
            ></QuizComponent>
        )}
        </div>
    </ThemeProvider>
  );
}
