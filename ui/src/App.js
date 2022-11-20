import React, { useCallback } from "react";
import Start from "./components/Start.js";
import QuizComponent from "./components/QuizComponent.js";

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
  );
}
