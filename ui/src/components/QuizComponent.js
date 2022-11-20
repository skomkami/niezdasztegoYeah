import React from "react";
import Quest from "./Quest.js";

export default function QuizComponent(props) {
  const [score, setScore] = React.useState(0);
  const [showAnswers, setShowAnswers] = React.useState(false);
  const [questions, setQuestions] = React.useState([]);
  const [allComplete, setAllComplete] = React.useState(false);
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;
  const topic = "";

  function playAgain() {
    props.showStartSetter(true);
    topic = props.topic;
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
  }, [showAnswers]);

  React.useEffect(() => {
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
  }, []);

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
    <div className="quiz-container">
      {quests}
      {showAnswers ? (
        <div className="button-container">
          <h3 className="button-container-score">
            {"You scored " + score + "/5 correct answers"}
          </h3>
          <button className="button" onClick={playAgain}>
            Play Again
          </button>
        </div>
      ) : (
        <button
          className="button"
          disabled={!allComplete}
          onClick={checkAnswers}
        >
          Check Answers
        </button>
      )}
    </div>
  );
}
