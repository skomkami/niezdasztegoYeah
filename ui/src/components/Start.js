import React from "react";

export default function Start(props) {
  const [topic, setTopic] = React.useState("");

  function handleChange({ target }) {
    setTopic(target.value);
  }

  return (
    <div className="start-container">
      <h1 className="start-container-title">Quizzical</h1>
      <h2 className="start-container-subtitle">Query Your Brain</h2>
      <input
        type="text"
        name="topicBox"
        placeholder="Enter topic here..."
        value={topic}
        onChange={handleChange}
      />
      <button
        className="start-container-button"
        onClick={props.startQuiz(topic)}
      >
        Start Quiz
      </button>
    </div>
  );
}
