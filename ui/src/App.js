import React, { useCallback } from "react";
import Start from "./components/Start.js";
import QuizComponent from "./components/QuizComponent.js";
import blob from "./images/blob.png";

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
          topic={this.topic}
        ></QuizComponent>
      )}
      <img className="blob1" src={blob} alt="" />
      <img className="blob2" src={blob} alt="" />
    </div>
  );
}
